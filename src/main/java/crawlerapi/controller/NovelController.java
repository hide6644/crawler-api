package crawlerapi.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
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
import crawlerapi.dto.NovelChapterInfoSummary;
import crawlerapi.dto.NovelChapterSummary;
import crawlerapi.dto.NovelInfoSummary;
import crawlerapi.dto.NovelSummary;
import crawlerapi.entity.Novel;
import crawlerapi.service.NovelService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/crawler-api")
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
                : novelService.searchIndex(searchParameters);
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
                        // .body(novel.getBody())
                        .novelInfoSummary(
                                NovelInfoSummary.builder()
                                        .id(novel.getNovelInfo().getId())
                                        .checkedDate(Date.from(ZonedDateTime.of(novel.getNovelInfo().getCheckedDate(), ZoneId.systemDefault()).toInstant()))
                                        .modifiedDate(Date.from(ZonedDateTime.of(novel.getNovelInfo().getModifiedDate(), ZoneId.systemDefault()).toInstant()))
                                        .finished(novel.getNovelInfo().isFinished())
                                        .keyword(novel.getNovelInfo().getKeyword())
                                        .favorite(novel.getNovelInfo().isFavorite())
                                        .rank(novel.getNovelInfo().getRank())
                                        .checkEnable(novel.getNovelInfo().isCheckEnable())
                                        .build())
                        .novelChapterSummary(novel.getNovelChapters().stream().map(novelChapter ->
                                NovelChapterSummary.builder()
                                        .id(novelChapter.getId())
                                        .url(novelChapter.getUrl())
                                        .title(novelChapter.getTitle())
                                        // .body(novelChapter.getBody())
                                        .novelChapterInfoSummary(
                                                NovelChapterInfoSummary.builder()
                                                .id(novelChapter.getNovelChapterInfo().getId())
                                                .checkedDate(Date.from(ZonedDateTime.of(novelChapter.getNovelChapterInfo().getCheckedDate(), ZoneId.systemDefault()).toInstant()))
                                                .modifiedDate(Date.from(ZonedDateTime.of(novelChapter.getNovelChapterInfo().getModifiedDate(), ZoneId.systemDefault()).toInstant()))
                                                .unread(novelChapter.getNovelChapterInfo().isUnread())
                                                .readDate(Date.from(ZonedDateTime.of(novelChapter.getNovelChapterInfo().getReadDate(), ZoneId.systemDefault()).toInstant()))
                                                .build())
                                        .build()))
                        .build())
                .build();
        return Mono.just(ResponseEntity.ok(novelSummaryResponse));
    }
}
