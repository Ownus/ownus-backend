package my.sideproject.ownus;

import my.sideproject.ownus.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OwnusApplicationTests {

	@Autowired
	UserRepository userRepository;
	@Test
	void contextLoads() {
	}


}
