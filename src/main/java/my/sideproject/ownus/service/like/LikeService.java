package my.sideproject.ownus.service.like;

import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.UserEntity;

public interface LikeService {
    boolean addLike(UserEntity user, Long product_id);
    boolean isAlreadyLike(UserEntity user, ProductEntity product);
}
