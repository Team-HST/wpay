package com.hst.wpay.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

/**
 * @author dlgusrb0808@gmail.com
 */
@Service
public class JwtService {

	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	private final String secretKey;

	public JwtService(@Value("${spring.jwt.secret-key}") String secretKey) {
		this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/***
	 * 인증토큰 유효성 검사
	 * @param token 인증토큰
	 * @return 유효여부
	 */
	public boolean isValidToken(String token) {
		try {
			if (StringUtils.isEmpty(token)) {
				logger.error("인증토큰을 확인할 수 없습니다.");
				return false;
			}
			Jws<Claims> claims = parse(token);
			logger.info("{}", claims);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			logger.error(String.format("인증토큰 형식이 잘못됐습니다. token: %s", token), e);
			return false;
		}
	}

	/***
	 * 인증토큰 파싱
	 * @param token 인증토큰
	 * @return 파싱결과
	 */
	public Jws<Claims> parse(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
	}

}
