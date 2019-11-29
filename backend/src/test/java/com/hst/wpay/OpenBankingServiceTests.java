package com.hst.wpay;

import com.hst.wpay.bankaccount.service.BankAccountService;
import com.hst.wpay.openbanking.OpenBankingService;
import com.hst.wpay.openbanking.model.response.OpenBankingAccountResponse;
import com.hst.wpay.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dlgusrb0808@gmail.com
 */
@SpringBootTest
public class OpenBankingServiceTests {

	@Autowired
	private UserService userService;

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private OpenBankingService openBankingService;

	private static final Logger logger = LoggerFactory.getLogger(OpenBankingServiceTests.class);

	private static final String ACCESS_TOKEN = " eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzUxNzM0Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE1ODI1NTYxODksImp0aSI6IjhmMzUzNjY5LWI3NTYtNGNjMi04ZWY0LWJkOTFhMTY0ZDM4NyJ9.TMj9T2KbSwSjB0z-uYDa-wLTaISVVBn9hi5RmG1sJO8";
	private static final String USER_SEQ_NUM = "1100751734";

	// 199160068057881027815015

	@Test
	void 사용자인증_계좌등록() {
		String processUrl = openBankingService.getAuthenticationProcessingUrl(2L);
		logger.info("processUrl: {}", processUrl);
	}

	@Test
	void getBankAccountTest() {
		OpenBankingAccountResponse response = openBankingService.getBankAccount(ACCESS_TOKEN, USER_SEQ_NUM);
		response.getResList().get(0);
		logger.info("{}", response);
	}

	@Test
	void userMeTest() {
		openBankingService.userMe(ACCESS_TOKEN, USER_SEQ_NUM);
	}

	@Test
	void releaseAccount() {

		openBankingService.transfer(userService.getUser(13L), bankAccountService.getBankAccount(13L), 50000);
	}
	
	@Test 
	void depositTest(){
	  openBankingService.deposit(userService.getUser(13L), bankAccountService.getBankAccount(13L), 50000);
	}

}
