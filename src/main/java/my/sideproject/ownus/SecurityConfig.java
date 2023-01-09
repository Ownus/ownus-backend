package my.sideproject.ownus;


import lombok.RequiredArgsConstructor;
import my.sideproject.ownus.auth.JwtSecurityConfig;
import my.sideproject.ownus.auth.JwtTokenProvider;
import my.sideproject.ownus.service.user.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
//        return new JwtAuthenticationFilter(jwtTokenProvider);
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.httpBasic().disable(); /*SpringSecurity에서 만들어주는 로그인 페이지를 안쓰기 위해*/
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();/*프론트엔드가 분리된 환경을 위해 Restful한 Api 형태가 되는데 이를 위해*/


        httpSecurity.authorizeRequests()
                        .antMatchers("/**","/users/login", "/exception/**").permitAll()
                        .anyRequest().authenticated();

        /*JWT Token을 위한 필터를 이따가 만들건데, 이 필터를 어느 위치에서 사용하겠다고 등록 해줘야 필터가 작동됨*/
//        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


//        httpSecurity.formLogin()
//                        .loginPage("/users/login").defaultSuccessUrl("/")
//                        .usernameParameter("user_id")
//                        .passwordParameter("password").loginProcessingUrl("/users/login")
//                        .permitAll();
        //로그아웃 처리
//        httpSecurity.logout()
//                .logoutUrl("/users/logout")
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true);
        /*토큰 인증 과정에서 발생하는 예외를 처리하기 위함*/
//        httpSecurity.exceptionHandling()
//                        .accessDeniedHandler(customAccessDeniedHandler)
//                        .authenticationEntryPoint(customAuthenticationEntryPoint);

        httpSecurity
                .apply(new JwtSecurityConfig(jwtTokenProvider));
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
