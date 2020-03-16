package crawlerapi.controller.model;

import crawlerapi.dto.NovelSummary;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class NovelSummaryResponse {

    private final NovelSummary novel;
}
