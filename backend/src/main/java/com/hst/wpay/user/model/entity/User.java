package com.hst.wpay.user.model.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hst.wpay.bankaccount.model.entity.BankAccount;
import com.hst.wpay.openbanking.model.response.OpenBankingOAuthTokenResponse;
import com.hst.wpay.wedding.model.entity.Wedding;

import lombok.Data;

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

	@Column(name ="bank_account_authorized_yn")
	private String bankAccountAuthorizedYn;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private BankAccount bankAccount;

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

	public boolean isBankAccountAuthorized() {
		return "Y".equals(this.bankAccountAuthorizedYn);
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
		this.bankAccountAuthorizedYn = "Y";
	}
}
