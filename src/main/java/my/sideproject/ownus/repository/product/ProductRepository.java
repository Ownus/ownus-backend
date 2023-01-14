package my.sideproject.ownus.repository.product;

import my.sideproject.ownus.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {

    Page<ProductEntity> findAll(Pageable pageable);

    void save(ProductEntity product);

    List<ProductEntity> dummyInsertAll(List<ProductEntity> productList);
}
