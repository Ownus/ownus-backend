package my.sideproject.ownus;

import my.sideproject.ownus.repository.JpaUserRepository;
import my.sideproject.ownus.repository.UserRepository;
import my.sideproject.ownus.service.UserService;
import my.sideproject.ownus.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class AppConfig {

    private EntityManager em;

    @Autowired
    public AppConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository());
    }
    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository(em);
    }
}
