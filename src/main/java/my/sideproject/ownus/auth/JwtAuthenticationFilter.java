package my.sideproject.ownus.auth;

import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.auth.exception.CustomException;
import my.sideproject.ownus.entity.role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        if(accessToken != null) {

            if(jwtTokenProvider.validateToken(accessToken))
            {
                this.setAuthentication(accessToken);
            }
            else if(!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {
                System.out.println("어머 이런? AccessToken이 다 되었네요? 리프래시 토큰 한번 볼게요!");
                /*리프래시 토큰 검증*/
                boolean validateRefreshToken = jwtTokenProvider.validateToken(refreshToken);
                /* 리프래시 토큰이 저장소에 존재하는지 확인 */
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);

                if(validateRefreshToken && isRefreshToken) { /* 둘다 만족 시 */
                    String user_id = jwtTokenProvider.getUserId(refreshToken);

                    UserRole role = jwtTokenProvider.getRole(user_id);

                    String newAccessToken = jwtTokenProvider.createAccessToken(user_id, role);

                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);

                    this.setAuthentication(newAccessToken);
                }
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
// https://velog.io/@solchan/%EB%B0%B1%EC%97%85-Refresh-Token-%EB%B0%9C%EA%B8%89%EA%B3%BC-Access-Token-%EC%9E%AC%EB%B0%9C%EA%B8%89 참고 블로그

//헤더에서 jwt를 받아온다.
//        String token = jwtTokenProvider.resolveAccessToken(request);
//        if(token != null && token.startsWith("Bearer "))
//            token = token.replace("Bearer ", "");
//        try {
//            // 유효한 토큰인지 확인. (유효성 검사)
//            if(token != null && jwtTokenProvider.validateToken(token)) {
//                //토큰 인증과정을 거친 결과를 authentication이라는 이름에 저장한다.
//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                //SecurityContext에 Authentication 객체를 저장한다.
//                //token이 인증된 상태를 유지하도록 context를 유지해준다
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception e) {
//            SecurityContextHolder.clearContext();
//            System.out.println("JwtAuthenticationFilter 과정에서 에러");
//            e.getStackTrace();
//            return ;
//        }
//        chain.doFilter(request, response);