package crawlerapi.controller;

import java.util.stream.Stream;

import crawlerapi.dto.NovelSummary;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NovelSummaryResponse {

    private final Stream<NovelSummary> novels;
}
