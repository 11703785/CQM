package com.platform.application.sysmanage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
public class ManageConfiguration {

	/**
	 * 密码加密.
	 *
	 * @param secret 密码加密Key
	 * @return 密码加密Bean
	 */
	@Bean(name = "passwordEncoder")
	public PasswordEncoder getPasswordEncoder(
			@Value("${INIT_KEY}") final CharSequence secret) {
		return new StandardPasswordEncoder(secret);
	}
}
