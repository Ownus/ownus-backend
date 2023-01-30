package my.sideproject.ownus.repository.like;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.entity.Like;
import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Repository
public class LikeJpaRepository implements LikeRepository{
    private EntityManagerFactory emf;

    private EntityManager em;

    private EntityTransaction tx;
    public LikeJpaRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Optional<Like> isUserLike(UserEntity user, ProductEntity product) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        Set<Like> likes = null;
        try {
            tx.begin();
            likes = product.getLikes();
            Iterator<Like> iter = likes.iterator();
            while (iter.hasNext()) {
                Like like = iter.next();
                if(like.getUser().equals(user))
                {
                    return Optional.ofNullable(like);
                }
            }
            tx.commit();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            em.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Like> save(Like like) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(like);
            tx.commit();
        }catch (Exception e) {
            e.getStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        return Optional.ofNullable(like);
    }
}
