package my.sideproject.ownus.repository.token;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.dto.token.RefreshToken;
import my.sideproject.ownus.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

@Repository
public class TokenJpaRepository implements TokenRepository{
    private EntityManager em;
    private EntityManagerFactory emf;

    private EntityTransaction tx;
    public TokenJpaRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public String save(RefreshToken refreshToken) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        try{
            tx.begin();
            em.persist(refreshToken);
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            System.out.println("실패");
        }
        finally {
            em.close();
        }
        return refreshToken.getRefresh_Token();
    }

    @Override
    public boolean existByRefreshToken(String token) {
        em = emf.createEntityManager();
        System.out.println("token = " + token);
        RefreshToken refreshToken = null;
        try {
            refreshToken = em.find(RefreshToken.class, token);
        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        finally {
            em.close();
        }
        return refreshToken != null;
    }

    @Override
    public String findRefreshTokenById(String user_id) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        String sql = "select rtk from refresh_token rtk where rtk.user_id = :user_id";
        RefreshToken refreshToken = null;
        try{
            tx.begin();
            System.out.println("트랜잭션 시작");
            refreshToken = em.createQuery(sql, RefreshToken.class)
                    .setParameter("user_id", user_id)
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
        return refreshToken.getRefresh_Token();
    }

    @Override
    public void removeRefreshTokenById(String user_id) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        String sql = "delete from refresh_token rtk where rtk.user_id = :user_id";
        try{
            tx.begin();
            em.createQuery(sql)
                    .setParameter("user_id", user_id)
                    .executeUpdate();
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            System.out.println(e.getStackTrace());
        }
        finally {
            em.close();
        }
    }

}
