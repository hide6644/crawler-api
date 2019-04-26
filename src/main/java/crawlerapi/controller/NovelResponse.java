package crawlerapi.controller;

import java.util.List;

import crawlerapi.entity.Novel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NovelResponse {

    private List<Novel> novels;
}
