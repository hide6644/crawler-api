package crawlerapi.controller;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.entity.User;
import crawlerapi.security.PBKDF2Encoder;
import crawlerapi.security.model.Role;
import crawlerapi.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final PBKDF2Encoder passwordEncoder;

    private final UserService userService;

    @PostMapping("/signup")
    public Mono<ResponseEntity<User>> signup(@RequestParam("username") String username,
            @RequestParam("password") String password) {
        // TODO Duplicateした場合の処理追加
        return Mono.just(ResponseEntity.ok(userService.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .enabled(true)
                .roles(Arrays.asList(Role.ROLE_USER)).build()).orElseGet(User::new)));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Mono<ResponseEntity<User>> findByUser(@RequestParam("username") String username) {
        return Mono.just(ResponseEntity.ok(userService.findByUsername(username).orElseGet(User::new)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
