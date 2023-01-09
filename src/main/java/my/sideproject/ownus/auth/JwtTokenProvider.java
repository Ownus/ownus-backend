package my.sideproject.ownus.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    //JWT 토큰 생성
    public String createToken(String user_id, UserRole role, Long tokenValid) {

        //payload 설정
        Date now = new Date();
//        Claims claims = Jwts.claims()
//                .setSubject("access_token")
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + accessTokenValidTime));

        Claims claims = Jwts.claims().setSubject(user_id);

        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(now) // 발행 시간 저장
                .setExpiration(new Date(now.getTime() + tokenValid)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, secretKey) //해싱 알고리즘 및 키 설정
                .compact(); //생성

//        return Jwts.builder()
//                .setHeaderParam("typ", "JWT") //헤더
//                .setClaims(claims) //페이로드
//                .signWith(SignatureAlgorithm.HS256, secretKey) //서명 사용할 암호화 알고리즘과 signature에 들어갈 secretKey 세팅
//                .compact();
    }

    public Authentication getAuthentication(String token) throws UnsupportedEncodingException {
        UserDetails user = userDetailService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String getUserId(String token) throws UnsupportedEncodingException {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String resolveAccessToken(HttpServletRequest request)
    {
        if(request.getHeader("Authorization") != null)
            return request.getHeader("Authorization");
        return null;
    }
    public String resolveRefreshToken(HttpServletRequest request)
    {
        if(request.getHeader("refreshToken") != null)
            return request.getHeader("refreshToken");
        return null;
    }
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("Authorization");
//    }

    public boolean validateToken(String jwtToken) {
        try{
            System.out.println("jwtToken = " + jwtToken);
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();

            System.out.println("jwtToken = " + jwtToken);
            System.out.println("claims = " + claims);
            
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken)
    {
        response.setHeader("authorization", "bearer " + accessToken);
    }
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



    public boolean existsRefreshToken(String refreshToken)
    {
        return tokenRepository.existByRefreshToken(refreshToken);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
