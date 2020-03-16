package crawlerapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import crawlerapi.entity.User;
import crawlerapi.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> save(final User user) {
        // TODO Duplicateした場合の処理追加
        return Optional.of(userRepository.saveAndFlush(user));
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findById(username);
    }
}
