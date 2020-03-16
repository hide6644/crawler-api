package crawlerapi.security.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class AuthResponse {

    private final String token;
}
