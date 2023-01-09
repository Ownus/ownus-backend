package my.sideproject.ownus.service.user;

import my.sideproject.ownus.dto.user.LoginDTO;
import my.sideproject.ownus.dto.user.RegisterDTO;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity register(RegisterDTO registerDTO) {
        UserEntity user = userRepository.save(UserEntity.toSaveEntity(registerDTO));
        System.out.println("user 세이브?");
        return user;
    }

    @Override
    public UserEntity ValidateById(String id) {
        UserEntity userEntity = userRepository.findById(id);
        if(userEntity != null) {
            return userEntity;
        }
        return null;
    }

    @Override
    public UserEntity ValidateByNickname(String nickname) {
        UserEntity user = userRepository.findByNickname(nickname);
        if(user != null)
        {
            return user;
        }
        return null;
    }

    /**
     * DB에 user_id랑 password를 대조시켜서 맞다면 user리턴
     * */
    @Override
    public UserEntity userLogin(LoginDTO loginDTO) {
        UserEntity user = userRepository.findById(loginDTO.getUser_id());
        if(user == null)
        {
            System.out.println("user_id 존재 x");
            return null;
        }
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
        {
            System.out.println("비밀번호 틀림 ㅋㅋ");
            System.out.println("user.getPassword() = " + user.getPassword());
            System.out.println("loginDTO.getPassword() = " + loginDTO.getPassword());
            return null;
        }
        return user;
    }
}
