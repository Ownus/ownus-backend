package my.sideproject.ownus.controller;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.dto.product.ProductEditDTO;
import my.sideproject.ownus.dto.product.ProductRegisterDTO;
import my.sideproject.ownus.entity.ProductImages;
import my.sideproject.ownus.entity.ProductEntity;
import my.sideproject.ownus.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    @PostMapping("/dummy")
    public ResponseEntity insertDummy() {
        List<ProductEntity> productList = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            ProductEntity product = new ProductEntity("product" + i,20000 + i, "description", "anywhere", new Date(),"N");
            productList.add(product);
        }
        productService.dummySave(productList);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity product(Pageable pageable) {
        Page<ProductEntity> productsList = productService.getProductsList(pageable);
        return new ResponseEntity<>(productsList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam("keyword") String keyword, Pageable pageable) {
        Page<ProductEntity> productsList = productService.search(keyword, pageable);
        return new ResponseEntity<>(productsList, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity register(@ModelAttribute ProductRegisterDTO productRegisterDTO) {
        ProductEntity newproduct = productService.register(productRegisterDTO);
        return new ResponseEntity<>(newproduct, HttpStatus.OK);
    }

    @GetMapping("/edit/{product_id}")
    public ResponseEntity editForm(@PathVariable Long product_id) {
        ProductEntity product = productService.findProductById(product_id);
        if(product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity(product, HttpStatus.OK);
    }
    @PutMapping("/edit/{product_id}")
    public ResponseEntity edit(@PathVariable Long product_id, @ModelAttribute ProductEditDTO productEditDTO) {
        ProductEntity product = productService.edit(product_id, productEditDTO);
        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PostMapping("/delete/{product_id}")
    public ResponseEntity delete(@PathVariable Long product_id) {
        ProductEntity product = productService.delete(product_id);
        return new ResponseEntity(product, HttpStatus.OK);
    }
}
