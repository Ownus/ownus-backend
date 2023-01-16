package my.sideproject.ownus.repository.product;

import my.sideproject.ownus.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {

    ProductEntity findById(Long id);
    Page<ProductEntity> findAll(Pageable pageable);

    ProductEntity save(ProductEntity product);

    List<ProductEntity> dummyInsertAll(List<ProductEntity> productList);

    Page<ProductEntity> findAllWithKeyword(String keyword, Pageable pageable);

    ProductEntity edit(Long id, ProductEntity product);
}
