package com.practice.efubaccount.post.service;

import com.practice.efubaccount.account.domain.Account;
import com.practice.efubaccount.account.service.AccountService;
import com.practice.efubaccount.post.domain.Post;
import com.practice.efubaccount.post.dto.response.PostResponse;
import com.practice.efubaccount.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private AccountService accountService;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostService postService;
    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = Account.builder()
                .email("efub@example.com")
                .password("password")
                .nickname("efub")
                .build();
    }

    @Test
    void PostService_생성_성공() {
        // TODO 2) 기본 stubbing
    }

    @Test
    void deletePost_postRepository_delete에서_예외_전달() {
        // TODO 3) 의도적 실패 주입 테스트
    }

    @Test
    void findAccountId_2번째호출에서_예외() {
        // given
        Account a1 = testAccount;
        Account a2 = Account.builder()
                .email("efub2@example.com")
                .password("testpw2")
                .nickname("efub2")
                .build();

        // TODO 4) 여러번 호출

        // TODO 5) 검증
    }

    @Test
    void getPost_조회수증가_호출검증() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .writer(testAccount)
                .build();


        // TODO 6) Mock 객체 확인
    }
}