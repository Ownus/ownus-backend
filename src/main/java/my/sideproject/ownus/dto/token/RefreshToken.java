package my.sideproject.ownus.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "refresh_token")
@Table(name="refresh_token")
public class RefreshToken {
    @Id
    @Column(nullable = false)
    private String refresh_Token;

    private String user_id;
}
