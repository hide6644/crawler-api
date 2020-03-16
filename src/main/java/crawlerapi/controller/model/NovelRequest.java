package crawlerapi.controller.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NovelRequest {

    private Long id;

    private NovelInfoRequest novelInfo;
}
