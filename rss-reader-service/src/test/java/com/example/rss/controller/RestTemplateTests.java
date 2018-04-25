package com.example.rss.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.JUnitRestDocumentation;

import com.example.rss.web.resources.DetailsNewsResource;
import com.example.rss.web.resources.NewsResource;

import wiremock.org.eclipse.jetty.http.HttpHeader;

public class RestTemplateTests extends BaseIntegrationTest {

    /**
     * RestTemplate is used for making the synchronous call
     */
    @Autowired
    TestRestTemplate restTemplate;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

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

    @Test
    public void testDnesbgNewsShouldWork() {
        ResponseEntity<DetailsNewsResource> response = this.restTemplate
                .getForEntity(VERSION_PREFIX + "/dnesbg/255086", DetailsNewsResource.class);
        DetailsNewsResource body = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(body.getTitle(), not(isEmptyString()));
        assertThat(body.getTitle(), is("ВСС единодушно реши: Владимира Янева е аут от СГС"));
        assertThat(body.getAuthorResource().getNames(), is(notNullValue()));
        assertThat(body.getInitialDate(), is(notNullValue()));
        assertThat(body.getUri(), not(isEmptyString()));

        // Test tags removal
        assertTrue(body.getNews().getNewsContent().startsWith("Висшият съдебен съвет единодушно реши да отстрани"));

    }

    @Test
    public void testCapitalNewsShouldWork() {
        ResponseEntity<DetailsNewsResource> response = this.restTemplate
                .getForEntity(VERSION_PREFIX + "/capital/3155166", DetailsNewsResource.class);
        DetailsNewsResource body = response.getBody();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(body.getTitle(), not(isEmptyString()));
        assertThat(body.getTitle(), is("ВЕИ-тата излизат на борсата, детайлът е кога точно"));
        assertThat(body.getAuthorResource().getNames(), is(notNullValue()));
        assertThat(body.getInitialDate(), is(notNullValue()));
        assertThat(body.getUri(), not(isEmptyString()));

        // Test tags removal
        assertTrue(body.getNews().getNewsContent().startsWith("Промените в Закона за енергетиката,"));
        assertTrue(body.getNews().getNewsContent()
                .endsWith("ще бъдат временно лишавани от правото си да извършват тази дейност."));

    }
}
