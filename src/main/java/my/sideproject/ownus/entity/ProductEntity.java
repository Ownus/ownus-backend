package my.sideproject.ownus.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

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

    /**
     * 등록일
     * */

    private String created_at;

    /**
     * 판매여부
     * */
    @Column
    private String is_sold;

    public ProductEntity(String p_name, int p_price, String description, String anywhere, String now, String n) {
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_info = description;
        this.thumbnail_url = anywhere;
        this.created_at = now;
        this.is_sold = "N";
    }
}
