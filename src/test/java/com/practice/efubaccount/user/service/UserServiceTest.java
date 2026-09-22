package com.practice.efubaccount.user.service;

import com.practice.efubaccount.user.dto.UserRequestDTO;
import com.practice.efubaccount.user.entity.Role;
import com.practice.efubaccount.user.entity.User;
import com.practice.efubaccount.user.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private ValidatorFactory validatorFactory;
    private Validator validator;
    private User testUser;

    @BeforeEach
    void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        testUser = User.builder()
                .id(1L)
                .name("김이화")
                .email("efub@test.com")
                .role(Role.USER)
                .build();
    }

    @AfterEach
    void tearDown() {
        validatorFactory.close();
    }

    @Test
    void throw_exception_when_email_is_duplicated() {
        // given
        UserRequestDTO dto = new UserRequestDTO("홍길동", "efub@test.com");
        given(userRepository.existsByEmail(dto.getEmail())).willReturn(true);
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.save(dto)
        );
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void prevent_non_admin_from_deleting_user() {
        // when & then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.delete(testUser.getId(), testUser)
        );
        assertEquals("권한이 없습니다.", exception.getMessage());
        verify(userRepository, never()).deleteById(anyLong());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "not-an-email"})
    void reject_invalid_email(String invalidEmail) {
        // given
        UserRequestDTO dto = UserRequestDTO.builder()
                .name("홍길동")
                .email(invalidEmail)
                .build();
        // when
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        // then
        assertFalse(violations.isEmpty());
    }

    @Test
    void find_user_by_id() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(testUser));
        // when
        User result = userService.findById(1L);
        // then
        assertEquals("김이화", result.getName());
        assertEquals("efub@test.com", result.getEmail());
        verify(userRepository).findById(1L);
    }
}
