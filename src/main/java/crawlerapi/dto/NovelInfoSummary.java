package crawlerapi.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import crawlerapi.entity.NovelInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelInfoSummary implements Serializable {

    private final Long id;

    private final LocalDateTime checkedDate;

    private final LocalDateTime modifiedDate;

    private final boolean finished;

    private final String keyword;

    private final boolean favorite;

    private final Integer rank;

    private final boolean checkEnable;

    public NovelInfo toEntity() {
        NovelInfo novelInfo = NovelInfo.builder()
                .checkedDate(checkedDate)
                .modifiedDate(modifiedDate)
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
