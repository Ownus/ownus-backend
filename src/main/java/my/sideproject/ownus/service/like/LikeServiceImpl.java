package my.sideproject.ownus.service.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.sideproject.ownus.entity.Like;
import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.repository.like.LikeRepository;
import my.sideproject.ownus.service.product.ProductService;
import my.sideproject.ownus.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final UserService userService;
    private final ProductService productService;

    private final LikeRepository likeRepository;
    @Override
    public boolean addLike(UserEntity user, Long product_id) {
        ProductEntity product = productService.findProductById(product_id);
        if(!isAlreadyLike(user, product)) {
            Like like = Like.toLikeEntity(user, product);
            like.setUser(user);
            like.setProduct(product);
            likeRepository.save(like);
            return true;
        }
        log.info("이미 좋아요를 눌러놨습니다 ㅎㅎ");
        return false;
    }

    @Override
    public boolean isAlreadyLike(UserEntity user, ProductEntity product) {
        return !likeRepository.isUserLike(user,product).isPresent();
    }
}
