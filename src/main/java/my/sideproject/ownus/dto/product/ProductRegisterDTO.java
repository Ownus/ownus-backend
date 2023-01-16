package my.sideproject.ownus.dto.product;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Id;
import java.util.List;

@Data
public class ProductRegisterDTO {

    private String p_name;

    private int p_price;

    private String p_info;

    private List<String> p_images;
}
