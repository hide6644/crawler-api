package crawlerapi.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crawlerapi.controller.model.SignupRequest;
import crawlerapi.entity.User;
import crawlerapi.security.PBKDF2Encoder;
import crawlerapi.security.model.Role;
import crawlerapi.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/crawler-api")
@RequiredArgsConstructor
public class UserController {

    private final PBKDF2Encoder passwordEncoder;

    private final UserService userService;

    @PostMapping("/signup")
    public Mono<ResponseEntity<User>> signup(@Valid @RequestBody SignupRequest request) {
        return Mono.just(ResponseEntity.ok(userService.save(User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .enabled(true)
                .roles(Arrays.asList(Role.ROLE_USER)).build()).orElseGet(User::new)));
    }

    @GetMapping("/users")
    public Mono<ResponseEntity<User>> findByUser(@RequestParam("username") String username) {
        return Mono.just(ResponseEntity.ok(userService.findByUsername(username).orElseGet(User::new)))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
