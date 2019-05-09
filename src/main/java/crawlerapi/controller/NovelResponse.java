package crawlerapi.controller;

import crawlerapi.entity.Novel;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;

@Getter
@Builder
public class NovelResponse {

    private Flux<Novel> novels;
}
