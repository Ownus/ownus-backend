package my.sideproject.ownus.controller;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.dto.RegisterDTO;
import my.sideproject.ownus.repository.UserRepository;
import my.sideproject.ownus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String register(@ModelAttribute RegisterDTO registerDTO) {
        userService.register(registerDTO);
        System.out.println("저장성공 ㅋㅋ");
        System.out.println("registerDTO.getUser_id() = " + registerDTO.getUser_id());
        System.out.println("registerDTO.getPassword() = " + registerDTO.getPassword());
        return "success";
    }
}
