package crawlerapi.controller;

import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.controller.model.NovelSummaryResponse;
import crawlerapi.dto.NovelInfoSummary;
import crawlerapi.dto.NovelSummary;
import crawlerapi.entity.Novel;
import crawlerapi.service.NovelService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/crawler-api")
@RequiredArgsConstructor
public class NovelController {

    private final NovelService novelService;

    @GetMapping("/novels")
    public Mono<ResponseEntity<NovelSummaryResponse>> search(
            @RequestParam(value = "search", required = false) String searchParameters) {
        Stream<Novel> novels = searchParameters == null
                ? novelService.findAll()
                : novelService.search(searchParameters);
        NovelSummaryResponse novelSummaryResponse = NovelSummaryResponse.builder()
                .novels(novels.map(novel -> NovelSummary.builder()
                        .id(novel.getId())
                        .url(novel.getUrl())
                        .title(novel.getTitle())
                        .writername(novel.getWritername())
                        .description(novel.getDescription())
                        .novelInfoSummary(
                                NovelInfoSummary.builder()
                                        .favorite(novel.getNovelInfo().isFavorite())
                                        .build())
                        .build()))
                .build();
        return Mono.just(ResponseEntity.ok(novelSummaryResponse));
    }

    @GetMapping("/novels/{id}")
    public Mono<ResponseEntity<Novel>> findById(@PathVariable("id") String id) {
        return Mono.just(ResponseEntity.ok(novelService.findById(Long.valueOf(id)).orElseThrow(RuntimeException::new)));
    }
}
