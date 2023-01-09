package my.sideproject.ownus;

import my.sideproject.ownus.repository.token.TokenJpaRepository;
import my.sideproject.ownus.repository.token.TokenRepository;
import my.sideproject.ownus.repository.user.UserJpaRepository;
import my.sideproject.ownus.repository.user.UserRepository;
import my.sideproject.ownus.service.user.UserService;
import my.sideproject.ownus.service.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;


@Configuration
public class AppConfig {


    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }
    @Bean
    public UserRepository userRepository() {
        return new UserJpaRepository(emf);
    }

    @Bean
    public TokenRepository tokenRepository()
    {
        return new TokenJpaRepository(emf);
    }
}
