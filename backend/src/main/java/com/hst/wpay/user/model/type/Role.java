package com.hst.wpay.user.model.type;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author dlgusrb0808@gmail.com
 */
public enum Role implements GrantedAuthority {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private String authority;

	Role(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
}
