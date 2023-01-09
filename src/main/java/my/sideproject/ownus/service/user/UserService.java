package my.sideproject.ownus.service.user;

import my.sideproject.ownus.dto.user.LoginDTO;
import my.sideproject.ownus.dto.user.RegisterDTO;
import my.sideproject.ownus.entity.UserEntity;

public interface UserService {
    UserEntity register(RegisterDTO registerDTO);
    UserEntity ValidateById(String id);

    UserEntity ValidateByNickname(String nickname);

    UserEntity userLogin(LoginDTO loginDTO);
}
