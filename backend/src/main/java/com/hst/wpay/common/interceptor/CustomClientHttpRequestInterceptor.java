package com.hst.wpay.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author dlgusrb0808@gmail.com
 */
public class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
		logger.debug("Request to: {}", httpRequest.getURI());
		traceRequest(httpRequest, bytes);
		ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
		logger.debug("Response code: {}", response.getRawStatusCode());
		traceResponse(response);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		logger.trace("============================= Request Begin =============================");
		logger.trace("URI           : {}", request.getURI());
		logger.trace("Method        : {}", request.getMethod());
		logger.trace("Headers       : {}", request.getHeaders());
		String requestBody = new String(body, StandardCharsets.UTF_8);
		logger.trace("Request Body  : {}", requestBody);
		logger.trace("============================== Request End ==============================");
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		StringBuilder responseBodyBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
		String line = bufferedReader.readLine();
		while (line != null) {
			responseBodyBuilder.append(line).append(System.lineSeparator());
			line = bufferedReader.readLine();
		}
		logger.trace("============================ Response Begin =============================");
		logger.trace("Status Code   : {}", response.getStatusCode());
		logger.trace("Status Text   : {}", response.getStatusText());
		logger.trace("Headers");
		response.getHeaders().forEach((headerName, headerValue) -> logger.trace("   {} : {}", headerName, headerValue));
		logger.trace("Response Body : {}", responseBodyBuilder.toString());
		logger.trace("============================= Response End ==============================");
	}
}
