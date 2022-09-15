package crawlerapi.controller.model;

import java.util.Map;
import java.util.stream.Stream;

import crawlerapi.dto.NovelSummary;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class NovelSummariesResponse {

    private final Stream<NovelSummary> novels;

    private final Map<String, Long> aggregateByKeywords;
}
