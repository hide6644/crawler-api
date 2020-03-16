package crawlerapi.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
                    matcher.group(1), matcher.group(2), matcher.group(3),
                    matcher.group(5), matcher.group(4), matcher.group(6));
        }

        return novelRepository.findAll(builder.build()).stream();
    }
}
