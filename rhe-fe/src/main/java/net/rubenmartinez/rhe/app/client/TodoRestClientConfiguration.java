package net.rubenmartinez.rhe.app.client;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import feign.Logger;
import feign.Request;

@EnableConfigurationProperties
@Validated
@ConfigurationProperties(prefix = "todoClient")
public class TodoRestClientConfiguration {
	
	@NotNull
	Integer connectionTimeout;
	@NotNull
	Integer readTimeout;

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public Request.Options options() {
		return new Request.Options(connectionTimeout, readTimeout);
	}
}
