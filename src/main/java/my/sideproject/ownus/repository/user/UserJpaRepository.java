package my.sideproject.ownus.repository.user;

import my.sideproject.ownus.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

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
            em.persist(user);
            tx.commit();
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
        System.out.println("user_id = " + user_id);
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
