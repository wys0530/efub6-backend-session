package com.practice.efubaccount.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.efubaccount.user.dto.UserRequestDTO;
import com.practice.efubaccount.user.entity.Role;
import com.practice.efubaccount.user.entity.User;
import com.practice.efubaccount.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockitoBean(types = JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    // 회원 생성 API 테스트를 작성해주세요.
    // Given
    // - name, email 준비
    // - UserRequestDTO 생성
    // - UserService.save()가 가짜 User를 반환하도록 Mocking
    //
    // When
    // - POST /users 요청
    //
    // Then
    // - HTTP 201 Created 검증
    // - JSON 응답의 name, email 값 검증
    // - userService.save() 호출 여부 검증
    @Test
    void create_user() throws Exception{
        String name = "김이화";
        String email = "efub@test.com";

        UserRequestDTO requestDTO = UserRequestDTO.builder()
                .name(name)
                .email(email)
                .build();

        User savedUser = User.builder()
                .id(1L)
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();

        given(userService.save(any(UserRequestDTO.class)))
                .willReturn(savedUser);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email));

        verify(userService).save(any(UserRequestDTO.class));
    }

}