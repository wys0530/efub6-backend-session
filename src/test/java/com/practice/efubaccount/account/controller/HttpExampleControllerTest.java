package com.practice.efubaccount.account.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HttpExampleController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockitoBean(types = JpaMetamodelMappingContext.class)
class HttpExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hello_요청에_이름이_포함된_문자열을_반환한다() throws Exception {
        // given
        String name = "EFUB";
        String expectedResponse = "helloEFUB";

        // when & then
        mockMvc.perform(get("/hello")
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}