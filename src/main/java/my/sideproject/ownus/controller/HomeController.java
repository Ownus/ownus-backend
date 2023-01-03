package my.sideproject.ownus.controller;

import my.sideproject.ownus.dto.User;
import my.sideproject.ownus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping("/hi")
    public String hello() {
        return "hi";
    }
}
