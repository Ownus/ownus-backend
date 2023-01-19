package my.sideproject.ownus.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.sideproject.ownus.dto.product.ProductRegisterDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
@Table(name = "product")
public class ProductEntity {

    /**
     * 상품 ID
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long product_id;

    /**
     * 상품명
     * */
    @Column
    private String p_name;

    /**
     * 상품 가격
     * */
    @Column
    private int p_price;

    /**
     * 상품 정보
     * */
    @Column
    private String p_info;

    /**
     * 썸네일 url
     * */
    @Column
    private String thumbnail_url;

    @OneToMany(mappedBy = "product")
    private List<ProductImages> images = new ArrayList<>();

    /**
     * 등록일
     * */

    private Date created_at;

    /**
     * 수정일
     * */
    private Date updated_at;
    /**
     * 판매여부
     * */
    @Column
    private String is_sold;

    public ProductEntity(String p_name, int p_price, String description, String anywhere, Date now, String n) {
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_info = description;
        this.thumbnail_url = "";
        this.created_at = now;
        this.is_sold = "N";
    }

    public static ProductEntity toSaveEntity(ProductRegisterDTO productRegisterDTO) {
        ProductEntity product = new ProductEntity();
        product.setP_info(productRegisterDTO.getP_info());
        product.setP_name(productRegisterDTO.getP_name());
        product.setP_price(productRegisterDTO.getP_price());
        product.setThumbnail_url(productRegisterDTO.getP_images().get(0));
        product.setCreated_at(new Date());
        product.setIs_sold("N");
        return product;
    }
}
