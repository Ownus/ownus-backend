package my.sideproject.ownus.repository.token;

import my.sideproject.ownus.dto.token.RefreshToken;
import my.sideproject.ownus.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class TokenJpaRepository implements TokenRepository{
    private EntityManager em;
    private EntityManagerFactory emf;

    private EntityTransaction tx;
    public TokenJpaRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public RefreshToken save(RefreshToken refreshToken) {
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
        return refreshToken;
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
}
