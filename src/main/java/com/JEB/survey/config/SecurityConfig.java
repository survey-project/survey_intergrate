package com.JEB.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig{
	//WebSecurityConfigurerAdapter에 오버로딩 되어있는 다양한 configure함수를  오버라이드해서 설정
	//security 버전 5.7부터는 WebSecurityConfigurerAdapter가 deprecate됨
	/*
	 * @Override protected void configure (HttpSecurity http) throws Exception{
	 * http.authorizeRequests().antMatchers("/**").permitAll(); }
	 */
	
	//따라서 필터체인을 구성하는 방법으로 SecurityFilterCahin을 Bean으로 선언하고 이때 HttpSecurity를 주입받아 사용하는 것
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().antMatchers("/js/**","/css/**","/img/**"); //spring security filterchain제외
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.authorizeRequests() //권한 검증 설정
				/*
				 * .antMatchers("/main","/joinForm","/survList").permitAll()
				 * .antMatchers("/member/**").permitAll()
				 */
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated() //어떤 URL로 접근하던지 인증이 필요
				.and()
				
			.formLogin() //폼방식 로그인을 사용할 것 
				.loginPage("/loginForm")
				.defaultSuccessUrl("/main", true)
				.permitAll().and() //로그인 성공시 이동
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //로그아웃 경로
				.logoutSuccessUrl("/loginForm") //로그아웃 성공시 이동 경로
				.invalidateHttpSession(true).and() //로그아웃 성공시 세션 제거
			.exceptionHandling()
				.accessDeniedPage("/denied").and() //권한이 없는 사용자가 접근했을 경우
			.build();			
	}
	
	
}
