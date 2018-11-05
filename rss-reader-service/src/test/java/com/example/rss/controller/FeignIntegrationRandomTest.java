package com.example.rss.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.rss.feign.RandomServiceProxy;

public class FeignIntegrationRandomTest extends BaseIntegrationTest {

    @Autowired
    private RandomServiceProxy feignRandom;

    @Test
    public void testLastDnesbg() {
        assertThat(feignRandom.randomNews().getAuthorResource().getNames(), is("Иван Иванов"));
        assertThat(feignRandom.randomNews().getUri(), is("https://m.dnes.bg/politika/2017/11/10/.290587"));
        assertThat(feignRandom.randomNews().getTitle(),
                is("Бионсе била готова с нов албум, чака да отшуми този на Адел"));
    }

}
