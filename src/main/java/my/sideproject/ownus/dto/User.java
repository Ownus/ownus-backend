package my.sideproject.ownus.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")

@Getter
@Setter
public class User {

    @Id
    private String user_id;


}
