package crawlerapi.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import crawlerapi.entity.Novel;
import crawlerapi.repository.NovelRepository;
import crawlerapi.repository.NovelSpecificationsBuilder;
import crawlerapi.repository.SearchOperation;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;

    public void updateFavorite(final Long id, final boolean favorite) {
        novelRepository.findById(id).ifPresent(novel -> novel.getNovelInfo().setFavorite(favorite));
    }

    public Stream<Novel> findAll() {
        return novelRepository.findAll().stream();
    }

    public Optional<Novel> findById(final Long id) {
        return novelRepository.findById(id);
    }

    public Stream<Novel> search(final String searchParameters) {
        NovelSpecificationsBuilder builder = new NovelSpecificationsBuilder();
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

        Specification<Novel> spec = builder.build();
        return novelRepository.findAll(spec).stream();
    }
}
