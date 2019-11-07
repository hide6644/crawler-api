package crawlerapi.controller;

import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.controller.model.NovelSummariesResponse;
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

    @PutMapping("/novels/{id}/favorite")
    public Mono<ResponseEntity<NovelInfoSummary>> updateFavorite(@PathVariable("id") Long novelId, @RequestBody boolean favorite) {
        novelService.saveFavorite(novelId, favorite);
        return Mono.just(ResponseEntity.ok(NovelInfoSummary.builder()
                .favorite(favorite)
                .build()));
    }

    @GetMapping("/novels")
    public Mono<ResponseEntity<NovelSummariesResponse>> search(
            @RequestParam(value = "search", required = false) String searchParameters) {
        Stream<Novel> novels = searchParameters == null
                ? novelService.findAll()
                : novelService.search(searchParameters);
        NovelSummariesResponse novelSummariesResponse = NovelSummariesResponse.builder()
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
        return Mono.just(ResponseEntity.ok(novelSummariesResponse));
    }

    @GetMapping("/novels/{id}")
    public Mono<ResponseEntity<NovelSummaryResponse>> findById(@PathVariable("id") String id) {
        Novel novel = novelService.findById(Long.valueOf(id));
        NovelSummaryResponse novelSummaryResponse = NovelSummaryResponse.builder()
                .novel(NovelSummary.builder()
                        .id(novel.getId())
                        .url(novel.getUrl())
                        .title(novel.getTitle())
                        .writername(novel.getWritername())
                        .description(novel.getDescription())
                        .novelInfoSummary(
                                NovelInfoSummary.builder()
                                        .checkedDate(novel.getNovelInfo().getCheckedDate().toString())
                                        .modifiedDate(novel.getNovelInfo().getModifiedDate().toString())
                                        .finished(novel.getNovelInfo().isFinished())
                                        .keyword(novel.getNovelInfo().getKeyword())
                                        .favorite(novel.getNovelInfo().isFavorite())
                                        .rank(novel.getNovelInfo().getRank())
                                        .checkEnable(novel.getNovelInfo().isCheckEnable())
                                        .build())
                        .build())
                .build();
        return Mono.just(ResponseEntity.ok(novelSummaryResponse));
    }
}
