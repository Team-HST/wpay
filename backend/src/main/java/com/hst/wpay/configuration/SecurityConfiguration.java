package com.hst.wpay.configuration;

import com.hst.wpay.jwt.filter.JwtAuthenticationFilter;
import com.hst.wpay.jwt.service.JwtService;
import com.hst.wpay.user.model.type.Role;
import com.hst.wpay.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author dlgusrb0808@gmail.com
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC_API = {"/**"};
	private static final String ADMIN_API_PATTERN = "/admin/**";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(PUBLIC_API).permitAll()
			.antMatchers(ADMIN_API_PATTERN).hasRole(Role.ADMIN.toString())
			.anyRequest().hasRole(Role.USER.toString())
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtService, userService),
					UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


}
