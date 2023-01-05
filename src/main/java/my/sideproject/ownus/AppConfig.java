package my.sideproject.ownus;

import my.sideproject.ownus.repository.UserJpaRepository;
import my.sideproject.ownus.repository.UserRepository;
import my.sideproject.ownus.service.UserService;
import my.sideproject.ownus.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
}
