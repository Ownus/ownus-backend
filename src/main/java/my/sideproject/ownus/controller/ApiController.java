package my.sideproject.ownus.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiController {

    @GetMapping("/hello")
    public String hello() {
        System.out.println("hihi");
        return "postman test success";
    }
}
