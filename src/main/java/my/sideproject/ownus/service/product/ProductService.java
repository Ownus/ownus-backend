package my.sideproject.ownus.service.product;

import my.sideproject.ownus.controller.ProductController;
import my.sideproject.ownus.dto.product.ProductEditDTO;
import my.sideproject.ownus.dto.product.ProductRegisterDTO;
import my.sideproject.ownus.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductEntity> getProductsList(Pageable pageable);

    List<ProductEntity> dummySave(List<ProductEntity> productList);

    Page<ProductEntity> search(String keyword, Pageable pageable);

    ProductEntity register(ProductRegisterDTO productRegisterDTO);

    ProductEntity findProductById(Long id);

    ProductEntity edit(Long id, ProductEditDTO productEditDTO);
}
