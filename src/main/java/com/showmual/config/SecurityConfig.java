package com.showmual.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
 
	private final UserDetailsService userDetailsService;
 
	@Bean
	public static BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
 
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		/* @formatter:off */
		http
			.authorizeRequests()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/scss/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/fonts/**").permitAll()
				.antMatchers("/", "/user/signup", "/user/login", "/analysis", "/test", "/user/imageTest", 
				        "/user/idCheck", "/user/nicknameCheck").permitAll() // 설정한 리소스의 접근을 인증절차 없이 허용
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/myinfo").hasRole("MEMBER")
				.anyRequest().authenticated() // 그 외 모든 리소스를 의미하며 인증 필요
			.and()
	            // csrf 토큰 무시시키기
	            .csrf()
	            .ignoringAntMatchers("/user/idCheck")
	            .ignoringAntMatchers("/user/nicknameCheck")
	        .and()
			    .formLogin()
				.permitAll()
				.loginPage("/user/login") // 기본 로그인 페이지
				.defaultSuccessUrl("/") // 로그인 성공 시 갈 페이지
			.and()
			    .logout()
				.permitAll()
				// .logoutUrl("/logout") // 로그아웃 URL (기본 값 : /logout)
				.logoutSuccessUrl("/") // 로그아웃 성공 URL (기본 값 : "/login?logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 주소창에 요청해도 포스트로 인식하여 로그아웃
				.deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 제거
				.invalidateHttpSession(true) // 로그아웃 시 세션 종료
				.clearAuthentication(true) // 로그아웃 시 권한 제거
			.and()
			    .exceptionHandling().accessDeniedPage("/user/denied");
				
		return http.build();
		/* @formatter:on */
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.httpFirewall(defaultHttpFirewall());
//	}
//	
//	@Bean
//	public HttpFirewall defaultHttpFirewall() {
//		return new DefaultHttpFirewall();
//	}
	
}
