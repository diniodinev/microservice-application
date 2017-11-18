package com.example.rss.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
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
@SpringBootTest(properties = { "spring.cloud.config.uri=http://localhost:12345", "eureka.client.enabled=false",
        "spring.datasource.url=jdbc:hsqldb:mem:ingest;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE" }, webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
public class RssApplicationShortTests {

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(WireMockSpring.options().port(12345));

    @Autowired
    GetRandomDnesBgNewsController getRandomDnesBgNewsController;

    @Autowired
    ExtractionNewsService extractionNewsService;

    @Test
    public void testIf404PageIsHitDoNotThrowException() {
        try {
            getRandomDnesBgNewsController.getSpecificNews(356084);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testIfBlogPhotoIsProcessed() {
        try {
            getRandomDnesBgNewsController.getSpecificNews(227619);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testIfNoImage() {
        try {
            getRandomDnesBgNewsController.getSpecificNews(18658);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
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
