package crawlerapi.dto;

import java.io.Serializable;

import crawlerapi.entity.NovelChapter;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NovelChapterSummary implements Serializable {

    private final Long id;

    private final String url;

    private final String title;

    private final String body;

    private final NovelChapterInfoSummary novelChapterInfoSummary;

    public NovelChapter toEntity() {
        NovelChapter novelChapter = NovelChapter.builder()
                .url(url)
                .title(title)
                .body(body)
                .novelChapterInfo(novelChapterInfoSummary.toEntity())
                .build();
        novelChapter.setId(id);
        return novelChapter;
    }
}
