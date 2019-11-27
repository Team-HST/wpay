package com.hst.wpay.configuration;

import lombok.Builder;
import lombok.Getter;

/**
 * @author dlgusrb0808@gmail.com
 */
@Builder
@Getter
public class OpenBankingAPIProperties {
	private String uri;
	private String appKey;
	private String appSecret;
	private String checksum;
	private String authorizeSuccessCallback;
	private String createAccessTokenSuccessCallback;
}
