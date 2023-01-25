package my.sideproject.ownus.repository.like;

import my.sideproject.ownus.entity.Like;
import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LikeJpaRepository implements LikeRepository{
    @Override
    public Optional<Like> isUserLike(UserEntity user, ProductEntity product) {
        return Optional.empty();
    }
}
