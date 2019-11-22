package com.hst.wpay.user.model.entity;

import com.hst.wpay.user.model.type.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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
	private Long seq;

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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "role"))
	private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
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
}
