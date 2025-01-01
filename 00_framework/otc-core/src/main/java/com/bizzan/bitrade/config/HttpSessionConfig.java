package com.bizzan.bitrade.config;

import com.bizzan.bitrade.ext.SmartHttpSessionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionIdResolver;


@EnableRedisHttpSession
public class HttpSessionConfig {

	@Bean
	public HttpSessionIdResolver httpSessionStrategy(){
		return new SmartHttpSessionStrategy();
	}
}
