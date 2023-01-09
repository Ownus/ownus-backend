package my.sideproject.ownus.repository.token;

import my.sideproject.ownus.dto.token.RefreshToken;

public interface TokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    boolean existByRefreshToken(String token);
}
