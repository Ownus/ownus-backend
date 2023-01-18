package my.sideproject.ownus.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "images")
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String image_path;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
