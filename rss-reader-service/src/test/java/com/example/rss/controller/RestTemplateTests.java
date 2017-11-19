package com.example.rss.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestTemplateTests extends BaseIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private static final String VERSION_PREFIX = "/v1";

    @Test
    public void testOldDnesbgNotAccessable() {
        ResponseEntity<String> responseException = this.restTemplate.getForEntity("/dnesbg/today", String.class);
        responseException.getStatusCode().compareTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void testLastDnesbg() {
        String body = this.restTemplate.getForObject(VERSION_PREFIX + "/dnesbg/today", String.class);
        assertThat(body, containsString("title"));
    }

    @Test
    public void testDnesbgWorldShouldWork() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(VERSION_PREFIX + "/dnesbg/world",
                String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
