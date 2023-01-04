package my.sideproject.ownus.repository;

import my.sideproject.ownus.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(String id);

    List<UserEntity> findAll();
}
