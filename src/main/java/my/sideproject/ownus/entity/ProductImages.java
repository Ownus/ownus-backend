package my.sideproject.ownus.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Override
    public String toString() {
        return "ProductImages{" +
                "id=" + id +
                ", image_path='" + image_path + '\'' +
                ", product=" + product +
                '}';
    }
}
