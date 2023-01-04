package my.sideproject.ownus.repository;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserJpaRepository implements UserRepository{

    private EntityManager em;
    private EntityManagerFactory emf;

    private EntityTransaction tx;
    public UserJpaRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public UserEntity save(UserEntity user) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        try{
            tx.begin();
            System.out.println("성공");
            em.persist(user);
            System.out.println("성공");
            tx.commit();
            System.out.println("성공");
        }
        catch (Exception e) {
            tx.rollback();
            System.out.println("실패");
        }
        finally {
            em.close();
        }
        return user;
    }

    @Override
    public Optional<UserEntity> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }
}
