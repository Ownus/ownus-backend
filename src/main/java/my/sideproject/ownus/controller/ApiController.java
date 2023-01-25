package my.sideproject.ownus.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ApiController {

    @GetMapping("/hello")
    public String hello() {

        System.out.println("hihi");
        return "postman test success";
    }
}
