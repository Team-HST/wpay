package com.hst.wpay.user.model.entity;

import com.hst.wpay.openbanking.model.response.OpenBankingOAuthTokenResponse;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dlgusrb0808@gmail.com
 */
@Entity
@Table(name ="USER")
@Data
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq")
	private Long sequence;

	@Column
	private String id;

	@Column
	private String password;

	@Column
	private String name;

	@Column(name = "access_token")
	private String bankAccessToken;

	@Column(name = "refresh_token")
	private String bankRefreshToken;

	@Column(name = "user_seq_num")
	private String bankUserSequenceNumber;

	@Column(name = "expires_at")
	private LocalDateTime expiresAt;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "role"))
	private Set<String> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/***
	 * OpenBanking 인증토큰 설정
	 * @param openBankingOAuthTokenResponse 인증토큰
	 */
	public void setOpenBankingTokenInfo(OpenBankingOAuthTokenResponse openBankingOAuthTokenResponse) {
		this.bankAccessToken = openBankingOAuthTokenResponse.getAccessToken();
		this.bankRefreshToken = openBankingOAuthTokenResponse.getRefreshToken();
		this.bankUserSequenceNumber = openBankingOAuthTokenResponse.getUserSeqNo();
		this.expiresAt = LocalDateTime.now().plus(openBankingOAuthTokenResponse.getExpiresIn(), ChronoUnit.SECONDS);
	}
}
