package crawlerapi.security.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class AuthRequest {

    private String username;

    private String password;
}
