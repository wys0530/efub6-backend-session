package com.practice.efubaccount.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    private User user;


    // TODO 3. 각 테스트 실행 전에 새로운 User 객체를 생성해주세요.
     @BeforeEach
    void setUp() {
         user = User.builder()
                 .id(1L)
                 .name("김이화")
                 .email("efub@test.com")
                 .role(Role.USER)
                 .build();
     }

    // TODO 4. User 객체가 정상적으로 생성되는지 테스트해주세요.
    // - null 여부 확인
    // - name, email, role 값 검증
    @Test
    void create_user() {
         assertNotNull(user);
         assertEquals(1L, user.getId());
         assertEquals("김이화", user.getName());
         assertEquals("efub@test.com", user.getEmail());
         assertEquals(Role.USER, user.getRole());
    }

    // TODO 5. changeName()을 호출했을 때 이름이 정상적으로 변경되는지 테스트해주세요.
    @Test
    void change_name() {
         // when
        user.changeName("홍길동");

        //then
        assertEquals("홍길동", user.getName());
    }

    // TODO 6. changeRole()을 호출했을 때 권한이 정상적으로 변경되는지 테스트해주세요.
    @Test
    void change_role() {
         // when
        user.changeRole(Role.ADMIN);

        // then
        assertEquals(Role.ADMIN, user.getRole());
    }
}
