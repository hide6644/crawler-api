package crawlerapi.dto;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import crawlerapi.entity.Novel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelSummary implements Serializable {

    private final Long id;

    private final String url;

    private final String title;

    private final String writername;

    private final String description;

    private final String body;

    private final boolean deleted;

    private final NovelInfoSummary novelInfoSummary;

    private final Stream<NovelChapterSummary> novelChapterSummary;

    public Novel toEntity() {
        Novel novel = Novel.builder()
                .url(url)
                .title(title)
                .writername(writername)
                .description(description)
                .body(body)
                .deleted(deleted)
                .novelInfo(novelInfoSummary.toEntity())
                .novelChapters(novelChapterSummary.map(novelChapter -> novelChapter.toEntity()).collect(Collectors.toList()))
                .build();
        novel.setId(id);
        return novel;
    }
}
