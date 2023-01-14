package my.sideproject.ownus.controller;

import my.sideproject.ownus.auth.JwtTokenProvider;
import my.sideproject.ownus.dto.token.RefreshToken;
import my.sideproject.ownus.dto.user.LoginDTO;
import my.sideproject.ownus.dto.user.RegisterDTO;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.entity.role.UserRole;
import my.sideproject.ownus.repository.token.TokenRepository;
import my.sideproject.ownus.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public String register(@ModelAttribute RegisterDTO registerDTO) {
        System.out.println("컨트롤러 왔다");
        registerDTO.setPassword(new BCryptPasswordEncoder().encode(registerDTO.getPassword()));
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

    @PostMapping("/login")
    public ResponseEntity<UserEntity> login(@ModelAttribute LoginDTO loginDTO, HttpServletResponse response) {
        UserEntity user = userService.userLogin(loginDTO);

        if(user == null) //로그인 실패했을 때 ㅇㅇ
        {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = jwtTokenProvider.createAccessToken(user.getUser_id(), user.getIs_admin());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUser_id(), user.getIs_admin());

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);

        RefreshToken saveRefreshToken = new RefreshToken(refreshToken, user.getUser_id());
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setComment("리프레시토큰");
        cookie.setPath("/");
        response.addCookie(cookie);

        tokenRepository.removeRefreshTokenById(user.getUser_id());
        tokenRepository.save(saveRefreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    @GetMapping("/jwt-test")
    public String jwtTest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer "))
        {
            token = token.substring(7);
        }
        if(jwtTokenProvider.validateToken(token))
        {
            return "유효합니다 ㅎㅎ 걱정마십시오";
        }
        else
        {
            return "토큰 만료됐습니다 ㄲㅈ";
        }
    }
}
