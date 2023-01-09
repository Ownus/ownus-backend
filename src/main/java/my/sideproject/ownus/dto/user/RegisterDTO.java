package my.sideproject.ownus.dto.user;

import lombok.*;
import my.sideproject.ownus.entity.UserEntity;

@Getter

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDTO {
    private String user_id;
    private String password;

    private String nickname;

}
