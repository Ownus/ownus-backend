package my.sideproject.ownus.dto.user;

import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

    private String user_id;
    private String password;
}
