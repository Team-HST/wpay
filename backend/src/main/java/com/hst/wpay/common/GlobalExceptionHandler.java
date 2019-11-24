package com.hst.wpay.common;

import com.hst.wpay.common.type.ResponseDescription;
import com.hst.wpay.user.exception.SigninFailException;
import com.hst.wpay.user.service.SignupFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dlgusrb0808@gmail.com
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({SigninFailException.class, SignupFailException.class})
	public ResponseEntity<DescribedResponse> handle(ReportableException e) {
		logger.error(e.getLog(), e);
		return ResponseEntity.badRequest().body(createErrorDescribedResponse(e));
	}

	private DescribedResponse createErrorDescribedResponse(ReportableException e) {
		return DescribedResponse.builder()
				.code(e.getDescription().getCode())
				.responseMessage(e.getDescription().getMessage())
				.responseCode(e.getDescription().toString())
				.build();
	}

}
