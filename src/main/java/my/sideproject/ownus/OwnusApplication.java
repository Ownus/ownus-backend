package my.sideproject.ownus;

import my.sideproject.ownus.entity.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootApplication
public class OwnusApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnusApplication.class, args);

//		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
//
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//		EntityTransaction transaction = entityManager.getTransaction();
//
//		try {
//			transaction.begin();
//
//			UserEntity user = new UserEntity("inseong12", "1234","12345","12345","Y");
//
//			entityManager.persist(user);
//
//			transaction.commit();
//		}
//		catch (Exception e) {
//			e.getStackTrace();
//
//			transaction.rollback();
//		}
//		finally {
//			entityManager.close();
//		}
//		entityManagerFactory.close();
	}

}
