package crawlerapi.service;

import static crawlerapi.repository.SearchParameter.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

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

    public void saveFavorite(final Long id, final boolean favorite) {
        Novel novel = findById(id);
        novel.getNovelInfo().setFavorite(favorite);
        novelRepository.save(novel);
    }

    public Stream<Novel> findAll() {
        return novelRepository.findAllByOrderByTitleAscWriternameAsc().stream();
    }

    public Novel findById(final Long id) {
        return novelRepository.findById(id).orElseThrow(NovelNotFoundException::new);
    }

    public Stream<Novel> search(final String searchParameters) {
        SearchSpecificationsBuilder<Novel> builder = createSpecificationsBuilder(searchParameters);
        return novelRepository.findAll(builder.build(), JpaSort.by(Novel.TITLE_FIELD_NAME, Novel.WRITERNAME_FIELD_NAME)).stream();
    }

    private SearchSpecificationsBuilder<Novel> createSpecificationsBuilder(final String searchParameters) {
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

        return builder;
    }
}
