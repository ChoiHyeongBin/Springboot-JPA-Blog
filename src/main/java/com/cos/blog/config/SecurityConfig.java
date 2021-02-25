package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration		// 빈 등록 (IoC 관리)
@EnableWebSecurity		// 시큐리티 필터가 등록이 됨
@EnableGlobalMethodSecurity(prePostEnabled = true)		// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean		// IoC 가 됨
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()	// csrf 토큰 비활성화 (테스트 시 걸어두는 게 좋음)
			.authorizeRequests()		// request 가 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
				.permitAll()				// '/auth/**' 로 들어오면 요청을 허용하고
				.anyRequest()			// '/auth/**' 가 아닌 다른 모든 요청은
				.authenticated()		// 인증이 되어야 한다
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");
	}
}
