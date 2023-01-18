package my.sideproject.ownus.repository.product;

import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.ProductImages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {

    ProductEntity findById(Long id);
    Page<ProductEntity> findAll(Pageable pageable);

    ProductEntity save(ProductEntity product, List<ProductImages> images);

    List<ProductEntity> dummyInsertAll(List<ProductEntity> productList);

    Page<ProductEntity> findAllWithKeyword(String keyword, Pageable pageable);

    List<String> getImagesURLById(Long id);
}
