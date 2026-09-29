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

    // TODO 7. UserRepository를 Mock 객체로 생성해주세요.
    @Mock
    private UserRepository userRepository;

    // TODO 8. Mock 객체를 주입받는 UserService를 생성해주세요.
    @InjectMocks
    private UserService userService;

    private Validator validator;
    private User testUser;

    // TODO 9. 각 테스트 실행 전에
    // 1) Validator를 생성하고
    // 2) 테스트용 User 객체를 초기화해주세요.
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        testUser = User.builder()
                .id(1L)
                .name("김이화")
                .email("efub@test.com")
                .role(Role.USER)
                .build();
    }


    // TODO 10. 중복 이메일이 존재하는 경우
    // IllegalArgumentException이 발생하는지 테스트해주세요.
    // 또한 repository.save()가 호출되지 않았는지 검증해주세요.
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


    // TODO 11. 일반 사용자가 회원 삭제를 시도하면
    // IllegalArgumentException이 발생하는지 테스트해주세요.
    // 또한 deleteById()가 호출되지 않았는지 검증해주세요.
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


    // TODO 12. @ParameterizedTest를 사용하여
    // 잘못된 이메일 형식들을 반복 검증해주세요.
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


    // TODO 13. id로 사용자를 조회했을 때
    // Repository가 반환한 User의 name과 email이 올바른지 검증해주세요.
    @Test
    void find_user_by_id() {
        // given
        given(userRepository.findById(1L)).willReturn(Optional.of(testUser));
        //when
        User result = userService.findById(1L);
        //then
        assertEquals("김이화", result.getName());
        assertEquals("efub@test.com", result.getEmail());
        verify(userRepository).findById(1L);
    }
}
