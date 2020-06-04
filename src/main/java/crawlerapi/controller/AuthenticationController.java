package crawlerapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.security.JWTUtil;
import crawlerapi.security.model.AuthRequest;
import crawlerapi.security.model.AuthResponse;
import crawlerapi.service.UserService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/crawler-api")
public class AuthenticationController {

    private final JWTUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final UserService userRepository;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
        return Mono.just(userRepository.findByUsername(ar.getUsername())).map(userDetails -> {
            if (userDetails.map(user -> passwordEncoder.matches(ar.getPassword(), user.getPassword())).orElse(false)) {
                return ResponseEntity.ok(AuthResponse.builder().token(userDetails.map(user -> jwtUtil.generateToken(user)).orElseThrow()).build());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
