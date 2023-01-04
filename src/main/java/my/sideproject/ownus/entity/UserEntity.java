package my.sideproject.ownus.entity;

import lombok.*;
import my.sideproject.ownus.dto.RegisterDTO;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private String user_id;
    private String nickname;
    private String password;

    private String profile_url;
    private String is_admin;

    public UserEntity(String user_id, String nickname, String password, String profile_url, String is_admin) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.password = password;
        this.profile_url = profile_url;
        this.is_admin = is_admin;
    }

    public UserEntity() {

    }
    public static UserEntity toSaveEntity(RegisterDTO registerDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUser_id(registerDTO.getUser_id());
        userEntity.setPassword(registerDTO.getPassword());
        userEntity.setNickname("inseong");
        userEntity.setIs_admin("Y");
        userEntity.setProfile_url("anywhere");
        return userEntity;
    }
}
