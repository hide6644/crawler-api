package crawlerapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.entity.User;
import crawlerapi.security.JWTUtil;
import crawlerapi.security.PBKDF2Encoder;
import crawlerapi.security.model.AuthRequest;
import crawlerapi.security.model.AuthResponse;
import crawlerapi.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JWTUtil jwtUtil;

    private final PBKDF2Encoder passwordEncoder;

    private final UserService userRepository;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
        return Mono.just(userRepository.findByUsername(ar.getUsername())).map(userDetails -> {
            if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.orElseGet(User::new).getPassword())) {
                return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails.orElseGet(User::new))));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
