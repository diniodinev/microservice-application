package com.example.rss.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.example.rss.config.security.AuditorAwareImpl;

@Configuration
public class RssConfiguration {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(RssConfiguration.class);

	@Value("classpath:/scripts/add-sites.sql")
	private Resource schemaScript;

	@Bean
	AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

	@Value("${ribbon.ConnectionTimeout:2000}")
	int connectionTimeout;

	@Value("${ribbon.ReadTimeout:5000}")
	int readTimeout;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplateRibbon(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.setConnectTimeout(connectionTimeout).setReadTimeout(readTimeout).build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	@Qualifier("dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
