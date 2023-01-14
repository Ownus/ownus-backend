package my.sideproject.ownus.service.product;

import my.sideproject.ownus.controller.ProductController;
import my.sideproject.ownus.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductEntity> getProductsList(Pageable pageable);
    void save(ProductEntity product);

    List<ProductEntity> dummySave(List<ProductEntity> productList);
}
