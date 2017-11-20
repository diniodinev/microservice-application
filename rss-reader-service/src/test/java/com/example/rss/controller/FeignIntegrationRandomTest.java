package com.example.rss.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;

import com.example.rss.feign.RandomServiceProxy;

public class FeignIntegrationRandomTest extends BaseIntegrationTest {

    @Autowired
    private RandomServiceProxy feignRandom;

    @Test
    public void testLastDnesbg() {
        assertThat(feignRandom.randomNews().getUri(), containsString("https://m.dnes.bg/"));
    }

}
