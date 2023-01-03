package my.sideproject.ownus.controller;

import my.sideproject.ownus.dto.RegisterDTO;
import my.sideproject.ownus.repository.UserRepository;
import my.sideproject.ownus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDTO registerDTO, HttpServletRequest request, HttpServletResponse response) {
        userService.register(registerDTO);
        return "success";
    }
}
