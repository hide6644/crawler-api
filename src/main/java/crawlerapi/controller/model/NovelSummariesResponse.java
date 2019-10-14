package crawlerapi.controller.model;

import java.util.stream.Stream;

import crawlerapi.dto.NovelSummary;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NovelSummariesResponse {

    private final Stream<NovelSummary> novels;
}
