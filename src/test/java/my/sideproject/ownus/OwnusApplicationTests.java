package my.sideproject.ownus;

import my.sideproject.ownus.dto.User;
import my.sideproject.ownus.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootTest
class OwnusApplicationTests {

	@Autowired
	UserRepository userRepository;
	@Test
	void contextLoads() {
	}


}
