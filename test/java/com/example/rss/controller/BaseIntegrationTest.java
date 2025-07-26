package com.example.rss.controller;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.rss.Application;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

@SpringBootTest(
        properties = { "spring.cloud.config.uri=http://localhost:12345", "eureka.client.enabled=false",
                "spring.datasource.url=jdbc:hsqldb:mem:ingest;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE" },
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = Application.class)
@RunWith(SpringRunner.class)
public class BaseIntegrationTest {

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(WireMockSpring.options().port(12345));

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void testOldDnesbgNotAccessable() {
        ResponseEntity<String> responseException = this.restTemplate.getForEntity("/dnesbg/today", String.class);
        responseException.getStatusCode().compareTo(HttpStatus.NOT_FOUND);
    }

}
