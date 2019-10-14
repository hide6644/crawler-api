package crawlerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crawlerapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
