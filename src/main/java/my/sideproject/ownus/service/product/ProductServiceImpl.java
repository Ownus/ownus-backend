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

        getProductImages(productRegisterDTO, product);
        return productRepository.save(product);
    }

    private static void getProductImages(ProductRegisterDTO productRegisterDTO, ProductEntity product) {
        List<String> images_path = productRegisterDTO.getP_images();

        List<ProductImages> images = product.getImages();
        for(String path : images_path) {
            ProductImages productImages = new ProductImages();
            productImages.setImage_path(path);
            productImages.setProduct(product);
            images.add(productImages);
        }
        product.setImages(images);
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
        return productRepository.update(product);
    }

    @Override
    public ProductEntity delete(Long id) {
        return productRepository.delete(id);
    }

    private static void toSaveEntityByEditForm(ProductEditDTO productEditDTO, ProductEntity product) {
        product.setP_info(productEditDTO.getP_info());
        product.setP_name(productEditDTO.getP_name());
        product.setP_price(productEditDTO.getP_price());
        product.setUpdated_at(new Date());

        List<String> images_path = productEditDTO.getP_images();
        List<ProductImages> images = product.getImages();
        images.clear();
        for(String path : images_path) {
            ProductImages productImages = new ProductImages();
            productImages.setImage_path(path);
            productImages.setProduct(product);
            images.add(productImages);
        }
        product.setImages(images);
        product.setThumbnail_url(images.get(0).getImage_path());
    }
}
