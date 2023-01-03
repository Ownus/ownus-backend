package my.sideproject.ownus.service;

import my.sideproject.ownus.dto.RegisterDTO;
import my.sideproject.ownus.dto.User;

public interface UserService {
    User register(RegisterDTO registerDTO);
}
