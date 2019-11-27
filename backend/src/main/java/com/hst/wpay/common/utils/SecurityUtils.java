package com.hst.wpay.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author dlgusrb0808@gmail.com
 */
public class SecurityUtils {

	public static UserDetails getCurrentAuthorizedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) authentication.getPrincipal();
	}

}
