package com.example.project.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
	
	@Autowired
	private DataSource dataSource;

	
	@Bean
	public SchedulerFactoryBeanCustomizer schedulerFactory() {
		return bean -> bean.setDataSource(dataSource);
	}
}
