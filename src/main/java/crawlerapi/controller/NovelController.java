package crawlerapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.dto.NovelSummary;
import crawlerapi.entity.Novel;
import crawlerapi.service.NovelService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class NovelController {

    private final NovelService novelService;

    @GetMapping("/novels")
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<NovelSummaryResponse>> findAll() {
        NovelSummaryResponse novelSummaryResponse = NovelSummaryResponse.builder()
                .novels(novelService.findAll()
                        .map(novel -> NovelSummary.builder()
                                .id(novel.getId())
                                .url(novel.getUrl())
                                .title(novel.getTitle())
                                .writername(novel.getWritername())
                                .description(novel.getDescription())
                                .build()))
                .build();
        return Mono.just(ResponseEntity.ok(novelSummaryResponse));
    }

    @GetMapping("/novels/{id}")
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<Novel>> findById(@PathVariable("id") String id) {
        return Mono.just(ResponseEntity.ok(novelService.findById(Long.valueOf(id)).orElseThrow(RuntimeException::new)));
    }
}
