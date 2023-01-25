package my.sideproject.ownus.repository.like;

import my.sideproject.ownus.entity.Like;
import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.UserEntity;

import java.util.Optional;

public interface LikeRepository {
    Optional<Like> isUserLike(UserEntity user, ProductEntity product);
}
