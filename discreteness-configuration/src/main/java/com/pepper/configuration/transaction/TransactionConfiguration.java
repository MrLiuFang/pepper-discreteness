package com.pepper.configuration.transaction;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;

/**
 * 
 * @author mrliu
 *
 */
@Configuration
@ConditionalOnProperty(name = "enabled.globalTransaction", havingValue = "true")
@ConditionalOnClass(value={GlobalTransactionScanner.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TransactionConfiguration {

	@Bean
	public GlobalTransactionScanner globalTransactionScanner(Environment environment) {
		return new GlobalTransactionScanner(environment.getProperty("spring.application.name","spring-boot-dubbo"),"my_test_tx_group");
	}
}
