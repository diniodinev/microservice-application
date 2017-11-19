package com.example.rss.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.rss.resources.NewsResource;

import wiremock.org.eclipse.jetty.http.HttpHeader;

public class RestTemplateTests extends BaseIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private static final String VERSION_PREFIX = "/v1";

    @Test
    public void testLastDnesbg() {
        String body = this.restTemplate.getForObject(VERSION_PREFIX + "/dnesbg/today", String.class);
        assertThat(body, containsString("title"));
    }

    @Test
    public void testDnesbgWorldShouldWork() {
        ResponseEntity<NewsResource> response = this.restTemplate.getForEntity(VERSION_PREFIX + "/dnesbg/world",
                NewsResource.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        response.getHeaders().containsKey(HttpHeader.CONTENT_TYPE);
        response.getHeaders().containsValue("application/hal+json;charset=UTF-8");

        assertTrue(response.getBody().getTitle().length() > 0);
        assertTrue(response.getBody().getUri().length() > 0);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void testDnesbgTodayAllShouldWork() {
        ResponseEntity<NewsResource[]> response = this.restTemplate.getForEntity(VERSION_PREFIX + "/dnesbg/today/all",
                NewsResource[].class);
        List<NewsResource> todaysNews = Arrays.asList(response.getBody());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(todaysNews.size(), is(not(0)));
    }

    @Test
    public void testDnesbgWorldAllShouldWork() {
        ResponseEntity<NewsResource[]> response = this.restTemplate.getForEntity(VERSION_PREFIX + "/dnesbg/world/all",
                NewsResource[].class);
        List<NewsResource> todaysNews = Arrays.asList(response.getBody());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(todaysNews.size(), is(not(0)));
    }
}
