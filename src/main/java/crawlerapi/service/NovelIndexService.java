package crawlerapi.service;

import static crawlerapi.repository.SearchParameter.*;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import jakarta.persistence.EntityManager;

import org.hibernate.search.engine.search.aggregation.AggregationKey;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crawlerapi.entity.Novel;
import crawlerapi.repository.SearchOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NovelIndexService {

    private final EntityManager entityManager;

    @Transactional
    public Stream<Novel> findAll() {
        return Search.session(entityManager)
                .search(Novel.class)
                .where(f -> f.matchAll())
                .sort(f -> f.field(Novel.TITLE_FIELD_NAME + Novel.SORT_NAME)
                        .then().field(Novel.WRITERNAME_FIELD_NAME + Novel.SORT_NAME))
                .fetchAllHits().stream();
    }

    @Transactional
    public Stream<Novel> search(final String searchParameters) {
        Matcher matcher = createMatcher(searchParameters);

        return Search.session(entityManager).search(Novel.class)
                .where(f -> f.bool(b -> {
                    b.must(f.matchAll());
                    while (matcher.find()) {
                        b.must(f.match()
                                .field(matcher.group(KEY))
                                .matching(matcher.group(VALUE)));
                    }
                }))
                .sort(f -> f.field(Novel.TITLE_FIELD_NAME + Novel.SORT_NAME)
                        .then().field(Novel.WRITERNAME_FIELD_NAME + Novel.SORT_NAME))
                .fetchAllHits().stream();
    }

    @Transactional
    public Map<String, Long> aggregateByKeywords() {
        AggregationKey<Map<String, Long>> countsByKeywordKey = AggregationKey.of("countsByKeyword");

        return Search.session(entityManager)
                .search(Novel.class)
                .where(f -> f.matchAll())
                .aggregation(countsByKeywordKey, f -> f.terms()
                        .field("novelInfo.keyword_facet", String.class)
                        .maxTermCount(10))
                .fetch(10)
                .aggregation(countsByKeywordKey);
    }

    private Matcher createMatcher(final String searchParameters) {
        String operationSetExper = String.join("|", SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile(
                "(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),",
                Pattern.UNICODE_CHARACTER_CLASS);
        return pattern.matcher(searchParameters + ",");
    }
}
