package crawlerapi.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NovelSummary implements Serializable {

    private final Long id;

    private final String url;

    private final String title;

    private final String writername;

    private final String description;
}
