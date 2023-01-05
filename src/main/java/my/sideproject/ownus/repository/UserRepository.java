package my.sideproject.ownus.repository;

import my.sideproject.ownus.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    UserEntity save(UserEntity user);

    UserEntity findById(String user_id);

    UserEntity findByNickname(String nickname);
    List<UserEntity> findAll();
}
