package my.sideproject.ownus.service.product;

import my.sideproject.ownus.dto.product.ProductEditDTO;
import my.sideproject.ownus.dto.product.ProductRegisterDTO;
import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.entity.ProductImages;
import my.sideproject.ownus.repository.product.ProductRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;
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
    public List<ProductEntity> dummySave(List<ProductEntity> productList) {
        System.out.println("productList = " + productList);
        return productRepository.dummyInsertAll(productList);
    }

    @Override
    public Page<ProductEntity> search(String keyword, Pageable pageable) {
        return productRepository.findAllWithKeyword(keyword, pageable);
    }

    @Override
    public ProductEntity register(ProductRegisterDTO productRegisterDTO) {
        /* DTO -> 엔티티로 변환 */
        ProductEntity product = ProductEntity.toSaveEntity(productRegisterDTO);
        List<ProductImages> images = getProductImages(productRegisterDTO, product);
        return productRepository.save(product, images);
    }

    private static List<ProductImages> getProductImages(ProductRegisterDTO productRegisterDTO, ProductEntity product) {
        List<String> images_path = productRegisterDTO.getP_images();
        List<ProductImages> images = new ArrayList<>();
        for(String path : images_path) {
            ProductImages productImages = new ProductImages();
            productImages.setImage_path(path);
            productImages.setProduct(product);
            images.add(productImages);
        }
        return images;
    }

    @Override
    public ProductEntity findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<String> getImagesURL(Long id) {
        List<String> images = productRepository.getImagesURLById(id);
        return images;
    }

    @Override
    public ProductEntity edit(Long id, ProductEditDTO productEditDTO) {
        ProductEntity product = findProductById(id);
        toSaveEntityByEditForm(productEditDTO, product);
        List<String> pImages = productEditDTO.getP_images();
        List<ProductImages> images = new ArrayList<>();
        for(String path : pImages) {
            ProductImages productImages = new ProductImages();
            productImages.setImage_path(path);
            productImages.setProduct(product);
            images.add(productImages);
        }
        return productRepository.save(product, images);
    }

    private static void toSaveEntityByEditForm(ProductEditDTO productEditDTO, ProductEntity product) {
        product.setP_info(productEditDTO.getP_info());
        product.setP_name(productEditDTO.getP_name());
        product.setP_price(productEditDTO.getP_price());
        product.setThumbnail_url(productEditDTO.getP_images().get(0));
        product.setUpdated_at(new Date());
    }
}
