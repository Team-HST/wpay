package com.hst.wpay.openbanking.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hst.wpay.openbanking.model.OpenBankingConstants;
import lombok.Data;
import lombok.ToString;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
@ToString
public class OpenBankingResponse {
	@JsonProperty("rsp_code")
	private String rspCode;
	@JsonProperty("rsp_message")
	private String rspMessage;
	
	@JsonProperty("bank_tran_date")
	@JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
	private String bank_tran_date;
	
	public boolean isSuccess() {
		return OpenBankingConstants.RSP_CODE_SUCCESS.equals(this.rspCode);
	}
}
