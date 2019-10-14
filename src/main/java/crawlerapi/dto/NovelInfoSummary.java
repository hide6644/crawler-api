package crawlerapi.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelInfoSummary implements Serializable {

    private final boolean favorite;
}
