package com.poscodx.jblog.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.poscodx.jblog.security.UserDetailsServiceImpl;

@SpringBootConfiguration
@EnableWebSecurity
public class SecurityConfig {

	/** filter chain[1]: web security filter
	 * 전역 접근 설정으로, 정적자원 요청을 배제한다. 
	*/
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web
            		.ignoring() 
            		.requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
            		.requestMatchers(new AntPathRequestMatcher("/assets/**"));
            }
        };
    }

	

	/** filter chain[2]: http security filter
	 * 세세한 접근 설정으로, 수많은 요청 중 filter로 보내줄 url 설정
	*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  
    	http
    		.logout()
    		.logoutUrl("/user/logout")
    		.logoutSuccessUrl("/")
    		.and()
    		// config Method 함수 호출로 인해 security configurer를 설정하게 된다. 
    		// ex) formLogin()은 3개의 filter 추가된다.
    		.formLogin()    
    		.loginPage("/user/login")
    		.loginProcessingUrl("/user/auth")
    		.usernameParameter("id")
    		.passwordParameter("password")
    		.defaultSuccessUrl("/")
    		// 로그인 실패 시 id 보내주기
    		.failureHandler(new AuthenticationFailureHandler() {
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					request.setAttribute("id", request.getParameter("id"));
					request
						.getRequestDispatcher("/user/login")
						.forward(request, response);
				}
			})
    		.and()
    		
    		.csrf()
    		.disable();
    	
    	// build를 하면 FilterChainProxy의 spring security filters [ () ,  ,   , ] 안에 필터들이 세팅된다.
    	return http.build();  
    }
    
    /** Authentication Manager
     * :UsernamePasswordAuthenticationFilter 에서 /user/auth 같은 url과 
     * param: id, password를 가져와서 Authentication Manager가 인증해준다.
	 * Bcryt Password Encoder와 User Details Service를 주입받아서 encode를 해준다. 
	 * UserDetailsService는 직접 만들었다.
	 * userVo 정보와 id, password로 들어온 정보를 비교를 해서 같으면 
	 * Security Context의 authenticated를 true로 바꿔주고 principal에 값을 세팅해준다. 
     */
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setPasswordEncoder(passwordEncoder);
    	authenticationProvider.setUserDetailsService(userDetailsService);
    	
    	return new ProviderManager(authenticationProvider);
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(4 /*4~31*/);
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
    	  return new UserDetailsServiceImpl();
    }
}
