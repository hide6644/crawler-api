package crawlerapi.controller.model;

import crawlerapi.dto.NovelSummary;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NovelSummaryResponse {

    private final NovelSummary novel;
}
