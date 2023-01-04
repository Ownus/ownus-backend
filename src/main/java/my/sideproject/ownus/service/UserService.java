package my.sideproject.ownus.service;

import my.sideproject.ownus.dto.RegisterDTO;
import my.sideproject.ownus.entity.UserEntity;

public interface UserService {
    UserEntity register(RegisterDTO registerDTO);
    UserEntity findById(String id);
}
