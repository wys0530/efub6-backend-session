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

    // TODO 2. GET /hello 요청에 대한 테스트를 작성해주세요.
    @Test
    void return_hello() throws Exception {
        // Given: name과 예상 응답값 준비
        String name = "EFUB";
        String expectedResponse = "helloEFUB";

        // When: GET /hello 요청에 name 쿼리 파라미터 전달
        mockMvc.perform(get("/hello")
                .param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        // Then: 200 OK와 응답 본문을 검증
    }


}