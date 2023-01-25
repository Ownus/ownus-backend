package my.sideproject.ownus.repository.product;

import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.ProductImages;
import my.sideproject.ownus.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductJpaRepository implements ProductRepository{

    private EntityManager em;
    private EntityManagerFactory emf;

    private EntityTransaction tx;
    public ProductJpaRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public ProductEntity findById(Long id) {
        em = emf.createEntityManager();
        ProductEntity product = new ProductEntity();
        String sql = "select p from product p join fetch p.images where p.product_id = :id";
        try {
            product = em.createQuery(sql, ProductEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            e.getStackTrace();
        }
        finally {
            em.close();
        }
        return product;
    }

    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        em = emf.createEntityManager();
        List<ProductEntity> productList = em.createQuery("select p from product p").getResultList();
        Long total = Long.parseLong(String.valueOf(productList.size()));
        em.close();
        return new PageImpl<>(productList, pageable,total);
    }

    @Override
    public ProductEntity save(ProductEntity product) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(product);
            List<ProductImages> images = product.getImages();
            for(ProductImages image : images) {
                em.persist(image);
            }
            tx.commit();
        }
        catch (Exception e) {
            tx.rollback();
            e.getStackTrace();
        }
        finally {
            em.close();
        }
        return product;
    }

    @Override
    public List<ProductEntity> dummyInsertAll(List<ProductEntity> productList) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        try {
            tx.begin();
            for (ProductEntity product : productList)
            {
                em.persist(product);
                System.out.println("product = " + product);
            }
            tx.commit();
        }catch (Exception e) {
            System.out.println("롤백됨");
            tx.rollback();
            e.getStackTrace();
        }finally {
            em.close();
        }
        return productList;
    }

    @Override
    public Page<ProductEntity> findAllWithKeyword(String keyword, Pageable pageable) {
        em = emf.createEntityManager();
        List<ProductEntity> productList = em.createQuery("select p from product p join fetch p.images where p.p_name like '%' || :keyword || '%'")
                .setParameter("keyword", keyword)
                .getResultList();
        Long total = Long.parseLong(String.valueOf(productList.size()));
        em.close();
        return new PageImpl<>(productList, pageable,total);
    }

    @Override
    public List<String> getImagesURLById(Long id) {
        em = emf.createEntityManager();
        List<String> list = em.createQuery("select p.images_url from product_image where p.id = :id")
                     .setParameter("id", id)
                     .getResultList();
        em.close();
        return list;
    }

    @Override
    public ProductEntity update(ProductEntity product) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(product);
            em.createQuery("delete from images i where product_id = :id")
                    .setParameter("id", product.getProduct_id())
                    .executeUpdate(); //기존에꺼 지우고 해야지
            List<ProductImages> images = product.getImages();
            for (ProductImages image : images) {
                em.merge(image);
            }
            tx.commit();
        }catch (Exception e) {
            e.getStackTrace();
            tx.rollback();
        }
        finally {
            em.close();
        }
        return product;
    }

    @Override
    public ProductEntity delete(Long id) {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        ProductEntity product = null;
        try {
            tx.begin();
            product = em.find(ProductEntity.class, id);
            em.remove(product);
            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            e.getStackTrace();
        }finally {
            em.close();
        }
        return product;
    }

}
