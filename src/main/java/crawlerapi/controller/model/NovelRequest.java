package crawlerapi.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovelRequest {

    private Long id;

    private NovelInfoRequest novelInfo;
}
