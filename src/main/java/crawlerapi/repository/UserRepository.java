package crawlerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crawlerapi.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
