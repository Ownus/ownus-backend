package my.sideproject.ownus.service;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.dto.RegisterDTO;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
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
    public UserEntity findById(String id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()) {
            return userEntity.get();
        }
        else return null;
    }
}
