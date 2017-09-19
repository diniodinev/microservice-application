package com.example.rss.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.rss.service.RssReaderService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(BbcController.class)
public class BbcControllerTest {

    @MockBean
    RssReaderService rssReaderService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void emptyUrlPassedFromTheConfiguration() throws Exception {
        given(rssReaderService.readFeed(null)).willReturn(null);

        this.mockMvc.perform(get("/bbc/topstories")).andExpect(status().isOk());

    }
}