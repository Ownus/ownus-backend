package my.sideproject.ownus.service;

import my.sideproject.ownus.dto.RegisterDTO;
import my.sideproject.ownus.dto.User;
import my.sideproject.ownus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        User user = new User();
        user.setUser_id(registerDTO.getUser_id());
        user.setPassword(registerDTO.getPassword());
        user.setNickname("inseong");
        user.setIs_admin("Y");
        user.setProfile_url("anywhere");
        userRepository.save(user);
        return user;
    }
}
