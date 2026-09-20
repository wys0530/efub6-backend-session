package com.practice.efubaccount.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("김이화")
                .email("efub@test.com")
                .role(Role.USER)
                .build();
    }

    @Test
    void create_user() {
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("김이화", user.getName());
        assertEquals("efub@test.com", user.getEmail());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void change_name() {
        // when
        user.changeName("홍길동");

        // then
        assertEquals("홍길동", user.getName());
    }

    @Test
    void change_role() {
        // when
        user.changeRole(Role.ADMIN);

        // then
        assertEquals(Role.ADMIN, user.getRole());
    }
}
