package my.sideproject.ownus.controller;

import com.mysql.cj.log.Slf4JLogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LikeController {

    @GetMapping("/like-api")
    public void test() {
        log.info("Slf4j 테스트");
    }
}
