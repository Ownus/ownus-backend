package my.sideproject.ownus.entity;

import lombok.*;
import my.sideproject.ownus.dto.user.RegisterDTO;
import my.sideproject.ownus.entity.role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {

//    @Autowired
//    private static PasswordEncoder passwordEncoder;
    @Id
    private String user_id;
    private String nickname;
    private String password;

    private String profile_url;
    private UserRole is_admin;

    public UserEntity(String user_id, String nickname, String password, String profile_url, UserRole is_admin) {
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
        userEntity.setIs_admin(UserRole.ROLE_USER);
        userEntity.setProfile_url("anywhere");
        return userEntity;
    }
}
