package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration		// 빈 등록 (IoC 관리)
@EnableWebSecurity		// 시큐리티 필터가 등록이 됨
@EnableGlobalMethodSecurity(prePostEnabled = true)		// 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailService principalDetailService;

	@Bean		// 어디에서든지 DI 해서 쓸 수 있음 (AuthenticationManager 를 Bean 으로 등록했으므로)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean		// IoC 가 됨
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는데 password 를 가로채기를 하는데
	// 해당 password 가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB 에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()	// csrf 토큰 비활성화 (테스트 시 걸어두는 게 좋음)
			.authorizeRequests()		// request 가 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
				.permitAll()				// '/auth/**' 로 들어오면 요청을 허용하고
				.anyRequest()			// '/auth/**', '/js/**' 등이 아닌 다른 모든 요청은
				.authenticated()		// 인증이 되어야 한다
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/")		// 스프링 시큐리티가 해당 주소로 요청이 오는 로그인을 가로채서 대신 로그인을 해줌
				.and()
				.oauth2Login()
				.loginPage("/loginForm");	// 구글 로그인이 완료된 뒤의 후처리가 필요 Tip. 코드X, (엑세스토큰 + 사용자프로필정보 O)
	}
}
