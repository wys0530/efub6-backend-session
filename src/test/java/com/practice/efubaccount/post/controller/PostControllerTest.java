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
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PostControllerTest {

    // 의존성 주입
    @Autowired MockMvc mockMvc;
    @Autowired AccountRepository accountRepository;
    @Autowired PostRepository postRepository;

    //  테스트용 DB 삽입
    @BeforeEach
    void seed(){
        Account account = Account.builder()
                .email("efub@example.com")
                .password("password")
                .nickname("efub")
                .build();
        accountRepository.save(account);
    }

    @Test
    @DisplayName("POST /posts → 201, Location 헤더 & H2에 실제 저장")
    void createPost_and_persist() throws Exception {
        // given
        String body = """
                {"title":"제목","content":"내용은다섯글자이상","accountId":1}
                """;

        //when
        MvcResult res = mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", matchesPattern("^/posts/\\d+$")))
                .andReturn();


        //then
        String location = res.getResponse().getHeader("Location");
        long id = Long.parseLong(java.net.URI.create(location).getPath().replace("/posts/", ""));
        assertTrue(postRepository.findById(id).isPresent());

    }

    @Test
    @DisplayName("GET /posts/{id} → 200 & 응답 필드 검증")
    void getPost_200() throws Exception {
        // given
        Account account = accountRepository.findAll().get(0);
        Post post = Post.builder()
                .title("제목")
                .writer(account)
                .content("내용은다섯글자이상")
                .build();

        post = postRepository.save(post);


        //when & then ($는 Json의 맨 위 root를 의미함)
        mockMvc.perform(get("/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목"));

    }
}
