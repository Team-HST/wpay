package com.hst.wpay.configuration;

import com.hst.wpay.common.interceptor.CustomClientHttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * @author dlgusrb0808@gmail.com
 */
@Configuration
public class OpenBankingConfiguration {

	@Bean
	public RestTemplate openBankingAPI() {
		HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new DefaultRedirectStrategy() {
			@Override
			protected boolean isRedirectable(String method) {
				return false;
			}
		}).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(requestFactory);
		RestTemplate restTemplate = new RestTemplate(bufferingClientHttpRequestFactory);
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		restTemplate.setInterceptors(Collections.singletonList(new CustomClientHttpRequestInterceptor()));
		return restTemplate;
	}

	@Bean
	public OpenBankingAPIProperties properties(
			@Value("${openbanking.url}") String apiUrl,
			@Value("${openbanking.checksum}") String checksum,
			@Value("${openbanking.auth.app-key}") String appKey,
			@Value("${openbanking.auth.app-secret}") String appSecret,
			@Value("${openbanking.authorize-success-callback}") String authorizeSuccessCallback
	) {
		return OpenBankingAPIProperties.builder()
				.uri(apiUrl)
				.checksum(checksum)
				.appKey(appKey)
				.appSecret(appSecret)
				.authorizeSuccessCallback(authorizeSuccessCallback)
				.build();
	}

}
