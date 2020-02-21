package crawlerapi.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import crawlerapi.entity.NovelChapterInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelChapterInfoSummary implements Serializable {

    private final Long id;

    private final Date checkedDate;

    private final Date modifiedDate;

    private final boolean unread;

    private final Date readDate;

    public NovelChapterInfo toEntity() {
        NovelChapterInfo novelChapterInfo = NovelChapterInfo.builder()
                .checkedDate(LocalDateTime.ofInstant(checkedDate.toInstant(), ZoneId.systemDefault()))
                .modifiedDate(LocalDateTime.ofInstant(modifiedDate.toInstant(), ZoneId.systemDefault()))
                .unread(unread)
                .readDate(LocalDateTime.ofInstant(readDate.toInstant(), ZoneId.systemDefault()))
                .build();
        novelChapterInfo.setId(id);
        return novelChapterInfo;
    }
}
