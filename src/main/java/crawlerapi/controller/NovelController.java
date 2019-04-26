package crawlerapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.entity.Novel;
import crawlerapi.service.NovelService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NovelController {

    private final NovelService novelService;

    @GetMapping("/novels")
    public ResponseEntity<NovelResponse> findAll() {
        List<Novel> novels = novelService.findAll();
        NovelResponse novelResponse = NovelResponse.builder()
                .novels(novels)
                .build();
        return new ResponseEntity<>(novelResponse, HttpStatus.OK);
    }
}
