package com.pepper.configuration.validator;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Validator验证模式
 * @author mrliu
 *
 */
@Configuration
public class ValidatorConfiguration {

	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
				// .addProperty( "hibernate.validator.fail_fast", "true" )
				.failFast(true).buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		return validator;
	}
}
