package my.sideproject.ownus.auth;

import io.jsonwebtoken.*;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.entity.role.UserRole;
import my.sideproject.ownus.repository.token.TokenRepository;
import my.sideproject.ownus.repository.user.UserRepository;
import my.sideproject.ownus.service.user.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.token.key}")
    private String secretKey;

    /*액세스 토큰 유효시간 30s */
    private Long accessTokenValidTime = 50 * 1000L;

    private Long refreshTokenValidTime = 240*60*10 * 1000L;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /*Access Token 생성*/
    public String createAccessToken(String user_id, UserRole role)
    {
        return this.createToken(user_id, role, accessTokenValidTime);
    }

    /*Refresh Token 생성*/

    public String createRefreshToken(String user_id, UserRole role)
    {
        return this.createToken(user_id, role, refreshTokenValidTime);
    }

    /**
     * 토큰을 생성한다.
     * 보통 accessToken에는 유저 정보(user_id, role)에 만료시기를 넣어서 암호화하고
     * refreshToken에는 만료기한만 저장한다.
     * 그래서 redis에 토큰하고 user를 식별할 수 있는 정보를 같이 넣어둔다는데 나는 일단 refreshToken에도 유저 정보를 담을 거임!
     * */
    public String createToken(String user_id, UserRole role, Long tokenValid) {
        //payload 설정
        Claims claims = Jwts.claims().setSubject(user_id);
        claims.put("role", role);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenValid);
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(now) // 발행 시간 저장
                .setExpiration(expiration) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, secretKey) //해싱 알고리즘 및 키 설정
                .compact(); //생성
    }

    public Authentication getAuthentication(String token) {
        UserDetails user = userDetailService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    /**
     * 액세스 토큰 뜯기
     * */
    public String resolveAccessToken(HttpServletRequest request)
    {
        if(request.getHeader("Authorization") != null)
            return request.getHeader("Authorization");
        return null;
    }
    /**
     * 리프레시 토큰 뜯기
     * */
    public String resolveRefreshToken(HttpServletRequest request) {
        if (request.getHeader("refreshToken") != null)
            return request.getHeader("refreshToken");
        return null;
    }
    /**
     * 토큰 검증하기
     * */
    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            return !claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * 응답 헤더에 액세스토큰 심어주기
     * */
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken)
    {
        response.setHeader("authorization", "bearer " + accessToken);
    }
    /**
     * 응답 헤더에 리프레시토큰 심어주기
     * */
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken)
    {
        response.setHeader("refreshToken", "bearer " + refreshToken);
    }


    public UserRole getRole(String user_id) {
        return userRepository.findById(user_id).getIs_admin();
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    public Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    public boolean existsRefreshToken(String refreshToken)
    {
        return tokenRepository.existByRefreshToken(refreshToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
