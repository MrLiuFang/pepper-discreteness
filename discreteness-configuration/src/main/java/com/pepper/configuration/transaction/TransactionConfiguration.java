package com.pepper.configuration.transaction;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;

/**
 * 
 * @author mrliu
 *
 */
@Configuration
@ConditionalOnClass(value={GlobalTransactionScanner.class})
public class TransactionConfiguration {

	@Bean
	public GlobalTransactionScanner globalTransactionScanner(Environment environment) {
		return new GlobalTransactionScanner(environment.getProperty("spring.application.name","spring-boot-dubbo"));
	}
}
