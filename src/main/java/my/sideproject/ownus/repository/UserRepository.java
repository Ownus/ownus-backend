package my.sideproject.ownus.repository;

import my.sideproject.ownus.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
}
