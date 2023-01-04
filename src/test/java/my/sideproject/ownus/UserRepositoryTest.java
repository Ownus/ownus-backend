package my.sideproject.ownus;

import my.sideproject.ownus.dto.RegisterDTO;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.repository.UserRepository;
import my.sideproject.ownus.service.UserService;
import my.sideproject.ownus.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public RegisterDTO newUser() {
        RegisterDTO registerDTO = new RegisterDTO("inseong12", "inse");
        return registerDTO;
    }
    @Test
    @Transactional
    @DisplayName("회원가입 테스트")
    public void memberSaveTest() {
        UserEntity register = userService.register(newUser());
        UserEntity findUser = userService.findById("inseong12");
        assertThat(register).isSameAs(findUser);
    }
}
