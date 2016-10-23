package com.pavlenko.manager.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author sergii.pavlenko
 * @since Oct 22, 2016
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.pavlenko.manager")
public class TransactionManagerConfiguration {
}
