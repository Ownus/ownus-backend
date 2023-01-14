package my.sideproject.ownus.repository.token;

import my.sideproject.ownus.dto.token.RefreshToken;

public interface TokenRepository {
    String save(RefreshToken refreshToken);
    boolean existByRefreshToken(String token);

    String findRefreshTokenById(String user_id);

    void removeRefreshTokenById(String user_id);
}
