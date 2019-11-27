package com.hst.wpay.openbanking.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
@ToString
public class OpenBankingAccountResponse extends OpenBankingResponse {
	// 공통
	@JsonProperty("api_tran_id")
	private String apiTranId;
	// 공통
	@JsonProperty("api_tran_dtm")
	private String apiTranDtm;

	@JsonProperty("user_name")
	private String userName;

	@JsonProperty("res_cnt")
	private String resCnt;

	@JsonProperty("res_list")
	private List<Account> resList;

	@Data
	@ToString
	public static class Account {
		@JsonProperty("fintech_use_num")
		private String fintechUseNum;
		@JsonProperty("account_alias")
		private String accountAlias;
		@JsonProperty("bank_code_std")
		private String bankCodeStd;
		@JsonProperty("bank_code_sub")
		private String bankCodeSub;
		@JsonProperty("bank_name")
		private String bankName;
		@JsonProperty("account_num_masked")
		private String accountNumMasked;
		@JsonProperty("account_holder_name")
		private String accountHolderName;
		@JsonProperty("account_type")
		private String accountType;
		@JsonProperty("inquiry_agree_yn")
		private String inquiryAgreeYn;
		@JsonProperty("inquiry_agree_dtime")
		private String inquiryAgreeDtime;
		@JsonProperty("transfer_agree_yn")
		private String transferAgreeYn;
		@JsonProperty("transfer_agree_dtime")
		private String transferAgreeDtime;
		@JsonProperty("account_state")
		private String accountState;
	}

}
