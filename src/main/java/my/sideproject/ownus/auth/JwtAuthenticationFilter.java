package my.sideproject.ownus.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.auth.exception.CustomException;
import my.sideproject.ownus.dto.token.RefreshToken;
import my.sideproject.ownus.entity.UserEntity;
import my.sideproject.ownus.entity.role.UserRole;
import my.sideproject.ownus.repository.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.Subject;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        if (!Objects.isNull(accessToken)) //액세스 토큰이 null이 아니면
        {
            accessToken = accessToken.substring(7);//"Bearer " 잘라버리기
            /* case 1 AccessToken이 유효할 떄 */
            if (jwtTokenProvider.validateToken(accessToken)) //유효하다면 토큰 검증만하고 시큐리티 컨텍스트에 등록
            {
                this.setAuthentication(accessToken);
            } else //리프래시 토큰을 봐야함 accesstoken에서 claim을 얻어낸 뒤 refreshtoken 정보 비교
            {
                Cookie[] cookies = request.getCookies();
                String refreshToken = "";
                for(Cookie cookie : cookies)
                {
                    if(cookie.getName().equals("refresh_token"))
                    {
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
                /* case 2 AccessToken은 만료 그러나 RefreshToken이 유효할때*/
                if (jwtTokenProvider.validateToken(refreshToken)) //리프레시 토큰이가 유효할 경우에
                {
                    // access_token 재발급
                    System.out.println("액세스토큰은 만료 그러나 리프레시토큰은 살아있음 고로 엑세스토큰 재발급 ㅎㅎ");
                    String user_id = jwtTokenProvider.getUserId(refreshToken);
                    UserRole role = jwtTokenProvider.getRole(user_id);
                    String newAccessToken = jwtTokenProvider.createAccessToken(user_id, role);
                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    this.setAuthentication(newAccessToken);
                }
                /* case 3 Refresh_Token도 죽었을때 로그인 화면으로 보내줘야하는데... */
            }
        }
        chain.doFilter(request, response);
    }
    public void setAuthentication(String token) throws UnsupportedEncodingException {
        /*토큰으로부터 유저 정보를 받아온다*/
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        /* 시큐리티컨텍스트에 Authenticaiton 객체를 저장한다 */
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}