package my.sideproject.ownus.service.user;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.auth.UserDetailsImpl;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(username);
        return new UserDetailsImpl(user);
    }
}
