package crawlerapi.service;

import static crawlerapi.repository.SearchParameter.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crawlerapi.entity.Novel;
import crawlerapi.exception.NovelNotFoundException;
import crawlerapi.repository.NovelRepository;
import crawlerapi.repository.SearchOperation;
import crawlerapi.repository.SearchSpecificationsBuilder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NovelService {

    private final NovelRepository novelRepository;

    private final EntityManager entityManager;

    public void saveFavorite(final Long id, final boolean favorite) {
        Novel novel = findById(id);
        novel.getNovelInfo().setFavorite(favorite);
        novelRepository.save(novel);
    }

    public Stream<Novel> findAll() {
        return novelRepository.findAll().stream();
    }

    public Novel findById(final Long id) {
        return novelRepository.findById(id).orElseThrow(NovelNotFoundException::new);
    }

    public Stream<Novel> search(final String searchParameters) {
        SearchSpecificationsBuilder<Novel> builder = new SearchSpecificationsBuilder<>();
        String operationSetExper = String.join("|", SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile(
                "(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),",
                Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchParameters + ",");

        while (matcher.find()) {
            builder.with(
                    matcher.group(OR_PREDICATE),
                    matcher.group(KEY),
                    matcher.group(OPERATION),
                    matcher.group(VALUE),
                    matcher.group(PREFIX),
                    matcher.group(SUFFIX));
        }

        return novelRepository.findAll(builder.build()).stream();
    }

    @Transactional
    public Stream<Novel> searchIndex(final String searchParameters) {
        SearchSession searchSession = Search.session(entityManager);
        String operationSetExper = String.join("|", SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile(
                "(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),",
                Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchParameters + ",");

        SearchResult<Novel> result = searchSession.search(Novel.class)
                .where(f -> f.bool(b -> {
                    b.must(f.matchAll());
                    while (matcher.find()) {
                        b.must(f.match()
                                .field(matcher.group(KEY))
                                .matching(matcher.group(VALUE)));
                    }
                }))
                .fetchAll();

        return result.hits().stream();
    }
}
