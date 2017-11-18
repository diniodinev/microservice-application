package com.example.rss;

import org.hsqldb.util.DatabaseManagerSwing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.rss.config.security.AuditorAwareImpl;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class Application {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    /** Active or not hsqldb explorer */
    private static final boolean DEBUG_DB = true;

    @Value("${spring.datasource.url}")
    private String databaseSourceUrl;

    @Autowired
    void setEnvironment(Environment e) {
        System.out.println(e.getProperty("configuration.projectName"));
    }

    /**
     * Application jar launcher.
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        runDatabaseManager();
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    /**
     * Run hsqldb explorer.
     */
    private static void runDatabaseManager() {
        if (DEBUG_DB) {
            System.setProperty("java.awt.headless", "false");
            DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:file:~/rss", "-noexit" });
        }
    }

}
