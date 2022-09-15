package crawlerapi.service;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import crawlerapi.entity.User;
import crawlerapi.security.model.Role;

@SpringBootTest
@Transactional
@Rollback
public class UserServiceTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    public void saveTest() {
        User user = userService.save(User.builder()
                .username("user1")
                .password(passwordEncoder.encode("user"))
                .email("user1@foo.bar")
                .enabled(true)
                .roles(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER)).build()).orElseGet(User::new);
        System.out.println(user);
    }

    @Test
    public void findByUsernameTest() {
        User user = userService.findByUsername("admin").orElseGet(User::new);
        System.out.println(user);
        user = userService.findByUsername("user").orElseGet(User::new);
        System.out.println(user);
    }
}
