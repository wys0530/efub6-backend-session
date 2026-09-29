package com.practice.efubaccount.post.controller;

import com.practice.efubaccount.account.domain.Account;
import com.practice.efubaccount.account.repository.AccountRepository;
import com.practice.efubaccount.post.domain.Post;
import com.practice.efubaccount.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO 7) 애노테이션 추가
class PostControllerTest {

    // TODO 8) 의존성 주입

    // TODO 9) 테스트용 DB 삽입

    @Test
    @DisplayName("POST /posts → 201, Location 헤더 & H2에 실제 저장")
    void createPost_and_persist() throws Exception {
        // given
        String body = """
                {"title":"제목","content":"내용은다섯글자이상","accountId":1}
                """;

        // TODO 10) when


        // TODO 11) then

    }

    @Test
    @DisplayName("GET /posts/{id} → 200 & 응답 필드 검증")
    void getPost_200() throws Exception {
        // TODO 12) given


        // TODO 13) when & then

    }
}
