package my.sideproject.ownus.controller;

import com.mysql.cj.log.Slf4JLogger;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.sideproject.ownus.auth.JwtAuthenticationFilter;
import my.sideproject.ownus.auth.JwtTokenProvider;
import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.service.like.LikeService;
import my.sideproject.ownus.service.product.ProductService;
import my.sideproject.ownus.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProductService productService;
    private final LikeService likeService;
    @GetMapping("/like-api")
    public void test() {
        log.info("Slf4j 테스트");
    }

    @PostMapping("/like/{product_id}")
    public ResponseEntity addLike(@PathVariable Long product_id, Principal principal) {
        String user_id = "";
        try {
            user_id = principal.getName();
        }catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userService.ValidateById(user_id);
        log.info("user = {}", user.toString());
        if(user != null)
        {
            likeService.addLike(user, product_id);
        }
        return new ResponseEntity(principal, HttpStatus.OK);
    }
}
