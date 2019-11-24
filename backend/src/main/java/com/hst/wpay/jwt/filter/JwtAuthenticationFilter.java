package com.hst.wpay.jwt.filter;

import com.hst.wpay.jwt.service.JwtService;
import com.hst.wpay.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dlgusrb0808@gmail.com
 */
public class JwtAuthenticationFilter extends GenericFilterBean {

	/***
	 * 인증토큰이 담긴 Request 헤더명
	 */
	private static final String AUTHORIZATION_HEADER = "Authorization";

	/***
	 * 인증토큰 타입
	 */
	private static final String TOKEN_TYPE = "Bearer";

	private final JwtService jwtService;
	private final UserService userService;

	public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String token = resolveToken((HttpServletRequest) request);
		if (jwtService.isValidToken(token)) {
			logger.info("인증토큰 유효성이 확인되었습니다.");
			Authentication authentication = userService.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		logger.info(String.format("HTTP 요청정보 : %s %s", request.getMethod(), request.getRequestURI()));
		String token = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.isEmpty(token)) {
			return null;
		} else if (!token.startsWith(TOKEN_TYPE)) {
			return null;
		}
		return token.substring(TOKEN_TYPE.length() + 1);
	}

}
