package my.sideproject.ownus.repository;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    /**
     * id로 user가 있는지 조회
     * */
    @Override
    public UserEntity findById(String user_id) {
        em = emf.createEntityManager();
        UserEntity user = null;
        try {
            user = em.find(UserEntity.class, user_id);
        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        finally {
            em.close();
        }
        return user;
    }

    /**
     * Nickname으로 User를 조회한다.
     * 얘는 createQuery를 통해 조회해봄
     * */
    @Override
    public UserEntity findByNickname(String nickname) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        String sql = "select u from users u where u.nickname = :nickname";
        UserEntity user = null;

        try{
            tx.begin();
            System.out.println("트랜잭션 시작");
            user = em.createQuery(sql, UserEntity.class)
                    .setParameter("nickname", nickname)
                    .getSingleResult();
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            System.out.println(e.getStackTrace());
        }
        finally {
            em.close();
        }
        return user;
    }

    @Override
    public List<UserEntity> findAll() {
        return em.createQuery("select u from users u", UserEntity.class)
                .getResultList();
    }
}
