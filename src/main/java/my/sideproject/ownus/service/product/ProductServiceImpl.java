package my.sideproject.ownus.service.product;

import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductEntity> getProductsList(Pageable pageable) {
        System.out.println("pageable.getPageSize() = " + pageable.getPageSize());
        System.out.println("pageable.getOffset() = " + pageable.getOffset());
        System.out.println("pageable.getPageNumber() = " + pageable.getPageNumber());
        System.out.println("pageable.toString() = " + pageable.toString());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("created_at").descending());
        return productRepository.findAll(pageRequest);
    }

    @Override
    public void save(ProductEntity product) {
        productRepository.save(product);
    }

    @Override
    public List<ProductEntity> dummySave(List<ProductEntity> productList) {
        System.out.println("productList = " + productList);
        return productRepository.dummyInsertAll(productList);
    }
}
