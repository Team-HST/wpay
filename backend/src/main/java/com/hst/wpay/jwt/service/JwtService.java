package com.hst.wpay.jwt.service;

import com.hst.wpay.common.utils.TimeUtils;
import com.hst.wpay.user.model.response.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
				return false;
			}
			Jws<Claims> claims = parse(token);
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

	/***
	 * 인증토큰 생성
	 * @param userSequence 사용자 시퀀스
	 * @param data 토큰에 포함할 데이터
	 * @return 인증토큰
	 */
	public <T> String createToken(Long userSequence, T data) {
		LocalDateTime now = LocalDateTime.now();
		Claims claims = Jwts.claims().setSubject(String.valueOf(userSequence));
		claims.put("data", data);

		return Jwts.builder()
				.setClaims(claims)	// claims 세팅을 위에서 해주는게 중요
				.setIssuedAt(TimeUtils.toDate(now))
				.setExpiration(TimeUtils.toDate(now.plusDays(1)))	// XXX 인증토큰 유효기간 일단은 1일로 잡음
				.signWith(SignatureAlgorithm.HS256, this.secretKey)
				.compact();
	}
}
