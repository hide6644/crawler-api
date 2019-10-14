package crawlerapi.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovelInfoRequest {

    private Long id;;

    private boolean favorite;
}
