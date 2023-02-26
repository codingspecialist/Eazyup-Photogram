package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration // IoC
public class SecurityConfig {

	private final OAuth2DetailsService oAuth2DetailsService;

	@Bean
	BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**").authenticated()
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.loginPage("/auth/signin") // GET
				.loginProcessingUrl("/auth/signin") // POST -> 스프링 시큐리티가 로그인 프로세스 진행
				.defaultSuccessUrl("/")
				.and()
				.oauth2Login() // form로그인도 하는데, oauth2로그인도 할꺼야!!
				.userInfoEndpoint() // oauth2로그인을 하면 최종응답을 회원정보를 바로 받을 수 있다.
				.userService(oAuth2DetailsService);
		return http.build();
	}

}
