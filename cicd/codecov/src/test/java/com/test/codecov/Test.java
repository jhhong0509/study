package com.test.codecov;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = CodecovApplication.class)
public class Test {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @org.junit.jupiter.api.Test
    public void test() throws Exception {
        MvcResult result = mvc.perform(get("/test")
                .queryParam("code", "bb"))
                .andExpect(status().isOk())
                .andReturn();

        String value = objectMapper.readValue(result.getResponse().getContentAsString(), String.class);
        Assertions.assertEquals(value, "hello");
    }

    @org.junit.jupiter.api.Test
    public void test2() throws Exception {
        MvcResult result = mvc.perform(get("/test")
                .queryParam("code", "aa"))
                .andExpect(status().isOk())
                .andReturn();

        String value = objectMapper.readValue(result.getResponse().getContentAsString(), String.class);
        Assertions.assertEquals(value, "not hello");
    }
}
