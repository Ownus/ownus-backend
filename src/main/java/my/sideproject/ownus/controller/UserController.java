package my.sideproject.ownus.controller;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.dto.RegisterDTO;
import my.sideproject.ownus.repository.UserRepository;
import my.sideproject.ownus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        System.out.println("컨트롤러 왔다");
        userService.register(registerDTO);
        return "success";
    }

    @PostMapping("/validation")
    public ResponseEntity validate(HttpServletRequest req) {
        if(req.getParameter("user-id") != null)
        {
            String user_id = (String) req.getParameter("user-id");
            if(userService.ValidateById(user_id) != null) //입력한 id 써도 될 때
            {
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        if(req.getParameter("nickname") != null)
        {
            String nickname = (String) req.getParameter("nickname");
            if(userService.ValidateByNickname(nickname) != null) //입력한 nickname 써도 될 때
            {
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
