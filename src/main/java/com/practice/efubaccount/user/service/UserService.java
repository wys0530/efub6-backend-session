package com.practice.efubaccount.user.service;

import com.practice.efubaccount.user.dto.UserRequestDTO;
import com.practice.efubaccount.user.entity.Role;
import com.practice.efubaccount.user.entity.User;
import com.practice.efubaccount.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        return userRepository.save(dto.toEntity());
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }

    @Transactional
    public void delete(Long id, User requester) {
        if (requester == null || requester.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        userRepository.deleteById(id);
    }
}
