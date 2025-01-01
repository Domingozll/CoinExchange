package com.bizzan.bitrade.config;

import com.bizzan.bitrade.ext.SmartHttpSessionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * 5个小时过期
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 18000)
public class HttpSessionConfig {

	@Bean
	public HttpSessionIdResolver httpSessionStrategy(){
		return new SmartHttpSessionStrategy();
	}
}
