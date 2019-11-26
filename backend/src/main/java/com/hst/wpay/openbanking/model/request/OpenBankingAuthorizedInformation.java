package com.hst.wpay.openbanking.model.request;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dlgusrb0808@gmail.com
 */
@Data
public class OpenBankingAuthorizedInformation {
	String code;
	String scope;
	String state;
	String client_info;
}
