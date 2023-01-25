package my.sideproject.ownus.service.like;

import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{

    @Override
    public boolean addLike(UserEntity user, Long product_id) {
        return false;
    }

    @Override
    public boolean isAlreadyLike(UserEntity user, ProductEntity product) {
        return false;
    }
}
