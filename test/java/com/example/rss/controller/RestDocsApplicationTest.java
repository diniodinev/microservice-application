package com.example.rss.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.rss.Application;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = { "spring.cloud.config.uri=http://localhost:12345", "eureka.client.enabled=false",
                "spring.datasource.url=jdbc:hsqldb:mem:ingest;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE" },
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = Application.class)
@AutoConfigureMockMvc
public class RestDocsApplicationTest {

    private static final String VERSION_PREFIX = "/v1";

    @ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(WireMockSpring.options().port(12345));

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation)).build();
    }

    @Test
    public void shouldReturnTodayDnesLastNews() throws Exception {
        this.mockMvc.perform(get(VERSION_PREFIX + "/dnesbg/today").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("title")))
                .andDo(document("dnesbgtoday",
                        responseFields(fieldWithPath("title").description("The title of the news"),
                                fieldWithPath("uri").description("The uri in the news site."),
                                subsectionWithPath("_links").description("Links with HATEOS resource."))));
    }

    @Test
    public void shouldReturnLastNewsFromWorld() throws Exception {
        this.mockMvc.perform(get(VERSION_PREFIX + "/dnesbg/world").accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("title")))
                .andDo(document("dnesbgworld",
                        responseFields(fieldWithPath("title").description("The title of the news"),
                                fieldWithPath("uri").description("The uri in the news site."),
                                subsectionWithPath("_links").description("Links with HATEOS resource."))));
    }

    @Test
    public void shouldReturnAllNewsFromWorld() throws Exception {
        this.mockMvc.perform(get(VERSION_PREFIX + "/dnesbg/world/all").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("title")))
                .andDo(document(
                        "dnesbgworldall",
                        responseFields(
                                subsectionWithPath("[]").type(JsonFieldType.ARRAY).description("The news content.")))
                        );
    }

}
