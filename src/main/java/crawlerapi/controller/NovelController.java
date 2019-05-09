package crawlerapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.service.NovelService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class NovelController {

    private final NovelService novelService;

    @GetMapping("/novels")
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<NovelResponse>> findAll() {
        NovelResponse novelResponse = NovelResponse.builder()
                .novels(novelService.findAll())
                .build();
        return Mono.just(ResponseEntity.ok(novelResponse));
    }
}
