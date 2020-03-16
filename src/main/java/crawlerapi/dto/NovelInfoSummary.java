package crawlerapi.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import crawlerapi.entity.NovelInfo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NovelInfoSummary implements Serializable {

    private final Long id;

    private final Date checkedDate;

    private final Date modifiedDate;

    private final boolean finished;

    private final String keyword;

    private final boolean favorite;

    private final Integer rank;

    private final boolean checkEnable;

    public NovelInfo toEntity() {
        NovelInfo novelInfo = NovelInfo.builder()
                .checkedDate(LocalDateTime.ofInstant(checkedDate.toInstant(), ZoneId.systemDefault()))
                .modifiedDate(LocalDateTime.ofInstant(modifiedDate.toInstant(), ZoneId.systemDefault()))
                .finished(finished)
                .keyword(keyword)
                .favorite(favorite)
                .rank(rank)
                .checkEnable(checkEnable)
                .build();
        novelInfo.setId(id);
        return novelInfo;
    }
}
