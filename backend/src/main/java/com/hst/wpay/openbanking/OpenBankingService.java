package com.hst.wpay.openbanking;

import com.hst.wpay.bankaccount.model.entity.BankAccount;
import com.hst.wpay.configuration.OpenBankingAPIProperties;
import com.hst.wpay.openbanking.model.response.OpenBankingAccountResponse;
import com.hst.wpay.openbanking.model.response.OpenBankingDepositResponse;
import com.hst.wpay.openbanking.model.OpenBankingConstants;
import com.hst.wpay.openbanking.model.response.OpenBankingOAuthTokenResponse;
import com.hst.wpay.openbanking.model.response.OpenBankingTransferWithdrawResponse;
import com.hst.wpay.user.model.entity.User;
import com.hst.wpay.wedding.model.entity.Wedding;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author dlgusrb0808@gmail.com
 */
@Service
public class OpenBankingService {
	private static final Logger logger = LoggerFactory.getLogger(OpenBankingService.class);

	private final RestTemplate openBankingAPI;
	private final OpenBankingAPIProperties properties;

	@Autowired
	public OpenBankingService(RestTemplate openBankingAPI, OpenBankingAPIProperties properties) {
		this.openBankingAPI = openBankingAPI;
		this.properties = properties;
	}

	/***
	 * OpenBanking 사용을 위한 사용자인증 취득 URL 반환
	 * @param seq 사용자 seq
	 * @return 인증 취득 URL
	 */
	public String getAuthenticationProcessingUrl(Long seq) {
		UriComponents uri = UriComponentsBuilder.fromUriString(properties.getUri() + "/oauth/2.0/authorize")
				.queryParam(OpenBankingConstants.RESPONSE_TYPE, OpenBankingConstants.RESPONSE_TYPE_CODE)
				.queryParam(OpenBankingConstants.CLIENT_ID, properties.getAppKey())
				.queryParam(OpenBankingConstants.REDIRECT_URI, properties.getAuthorizeSuccessCallback())
				.queryParam(OpenBankingConstants.SCOPE, OpenBankingConstants.SCOPE_ALL)
				.queryParam(OpenBankingConstants.CLIENT_INFO, seq)
				.queryParam(OpenBankingConstants.AUTH_TYPE, OpenBankingConstants.AUTH_TYPE_INIT)
				.queryParam(OpenBankingConstants.STATE, properties.getChecksum())
				.build();

		ResponseEntity<String> response = openBankingAPI.getForEntity(uri.toUriString(), String.class);
		return Objects.requireNonNull(response.getHeaders().getLocation()).toString();
	}

	/***
	 * OpenBanking OAuth 토큰 발행
	 * @param authorizedCode 토큰 발행 요청에 필요한 1회성 인증코드
	 * @return OAuth 토큰
	 */
	public OpenBankingOAuthTokenResponse issueOauthToken(String authorizedCode) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add(OpenBankingConstants.CODE, authorizedCode);
		params.add(OpenBankingConstants.CLIENT_ID, properties.getAppKey());
		params.add(OpenBankingConstants.CLIENT_SECRET, properties.getAppSecret());
		params.add(OpenBankingConstants.GRANT_TYPE, OpenBankingConstants.GRANT_TYPE_AUTHORIZATION_CODE);
		params.add(OpenBankingConstants.REDIRECT_URI, properties.getAuthorizeSuccessCallback());

		OpenBankingOAuthTokenResponse openBankingOAuthTokenResponse = openBankingAPI.postForEntity(properties.getUri() + "/oauth/2" +
				".0/token", params, OpenBankingOAuthTokenResponse.class).getBody();

		if (openBankingOAuthTokenResponse == null) {
			logger.error("인증토큰 정보 발급 실패");
			throw new RuntimeException();
		}

		return openBankingOAuthTokenResponse;
	}

	/***
	 * 사용자 대상 OpenBanking API 토큰 발급
	 * @param openBankingAccessToken OpenBanking 인증토큰
	 * @param openBankingUserSequenceNumber OpenBanking 사용자 ID
	 * @return
	 */
	public OpenBankingAccountResponse getBankAccount(String openBankingAccessToken, String openBankingUserSequenceNumber) {
		UriComponents uri = UriComponentsBuilder.fromUriString(properties.getUri() + "/v2.0/user/me")
				.queryParam(OpenBankingConstants.USER_SEQ_NO, openBankingUserSequenceNumber)
				.queryParam(OpenBankingConstants.INCLUDE_CANCEL_YN, OpenBankingConstants.NO)
				.queryParam(OpenBankingConstants.SORT_ORDER, OpenBankingConstants.SORT_ORDER_DESC).build();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(openBankingAccessToken);

		HttpEntity entity = new HttpEntity(headers);

		return openBankingAPI.exchange(uri.toUri(), HttpMethod.GET, entity, OpenBankingAccountResponse.class).getBody();
	}

	public void userMe(String openBankingAccessToken, String openBankingUserSequenceNumber) {
		UriComponents uri = UriComponentsBuilder.fromUriString(properties.getUri() + "/v2.0/user/me")
				.queryParam(OpenBankingConstants.USER_SEQ_NO, openBankingUserSequenceNumber).build();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(openBankingAccessToken);
		HttpEntity entity = new HttpEntity(headers);

		String accounts = openBankingAPI.exchange(uri.toUriString(), HttpMethod.GET, entity, String.class).getBody();

		logger.info("AAA : {}", accounts);
	}

	public OpenBankingTransferWithdrawResponse transfer(User user, BankAccount bankAccount, long amount) {
		Map<String, String> params = new HashMap<>();
		params.put("bank_tran_id", generateBankTransferId());
		params.put("cntr_account_type", "C");
		params.put("cntr_account_num", "0049082529");
		params.put("dps_print_content", "웨딩페이 출금");
		params.put("fintech_use_num", bankAccount.getFintechUseNumber());
		params.put("tran_amt", String.valueOf(amount));
		params.put("tran_dtime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
		params.put("req_client_name", user.getName());
		params.put("req_client_num", String.valueOf(user.getSequence()));
		params.put("req_client_fintech_use_num", bankAccount.getFintechUseNumber());
		params.put("transfer_purpose", "TR");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(user.getBankAccessToken());
		HttpEntity entity = new HttpEntity(params, headers);

		OpenBankingTransferWithdrawResponse accounts = openBankingAPI.exchange(properties.getUri() + "/v2.0/transfer/withdraw/fin_num", HttpMethod.POST, entity,
				OpenBankingTransferWithdrawResponse.class).getBody();

		return accounts;
	}
	
	public OpenBankingDepositResponse deposit(User user, BankAccount bankAccount, long amount) {
	  Map<String, String> params = new HashMap<>();
	  List<Map<String, String>> reqList = new ArrayList<>();
	  Map<String, String> map = new HashMap<>();
	  map.put("--tran_no", "1");
	  map.put("--bank_tran_id", generateBankTransferId());
	  map.put("--fintech_use_num", bankAccount.getFintechUseNumber());
	  map.put("--print_content", "예식비 정산 금액");
	  map.put("--tran_amt", String.valueOf(amount));
	  map.put("--req_client_name", user.getName());
    //params.put("--req_client_bank_code", bankAccount.getFintechUseNumber());
	  map.put("--req_client_account_num", String.valueOf(user.getSequence()));
	  map.put("--transfer_purpose", "TR");
	  reqList.add(map);
	  
    params.put("cntr_account_type", "C");
    params.put("cntr_account_num", "0049082529");
    params.put("wd_pass_phrase", "123456789");
    params.put("wd_print_content", "혼주 정산 금액");
    params.put("name_check_option", "on");
    params.put("tran_dtime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    params.put("req_cnt", "1");
    params.put("req_list", reqList.toString());
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(user.getBankAccessToken());
    HttpEntity entity = new HttpEntity(params, headers);
    
    OpenBankingDepositResponse accounts = openBankingAPI.exchange(properties.getUri() + "/v2.0/transfer/deposit/fin_num", HttpMethod.POST, entity,
        OpenBankingDepositResponse.class).getBody();
    
    return accounts;
	}
	

  private String generateBankTransferId() {
		return "T991600680U" + RandomStringUtils.randomNumeric(9);
	}

}
