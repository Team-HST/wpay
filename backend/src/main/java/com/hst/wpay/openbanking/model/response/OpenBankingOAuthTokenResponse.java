package com.hst.wpay.openbanking.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
@ToString
public class OpenBankingOAuthTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private Long expiresIn;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("user_seq_no")
	private String userSeqNo;


}
