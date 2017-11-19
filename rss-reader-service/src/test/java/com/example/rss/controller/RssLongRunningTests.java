package com.example.rss.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.rss.Application;
import com.example.rss.service.ExtractionNewsService;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = { "spring.cloud.config.uri=http://localhost:12346", "eureka.client.enabled=false",
                "spring.datasource.url=jdbc:hsqldb:mem:ingest;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE" },
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = Application.class)
public class RssLongRunningTests {

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(WireMockSpring.options().port(12346));

    @Autowired
    GetRandomDnesBgNewsController getRandomDnesBgNewsController;

    @Autowired
    ExtractionNewsService extractionNewsService;

    @Test
    public void testSlideShowImages() {
        getRandomDnesBgNewsController.getSpecificNews(357158);
    }

    @Test
    public void testLargeAmountOf25LastNews() {
        getRandomDnesBgNewsController.lastN(25);
    }

    @Test
    public void testLargeAmountOf25RandomNews() {
        getRandomDnesBgNewsController.randomNews(25);
    }

    @AfterClass
    public static void logout() {
        try {
            Connection conn = DriverManager
                    .getConnection("jdbc:hsqldb:mem:ingest;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
