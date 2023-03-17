package com.example.baekboom.backend.service;

import com.example.baekboom.backend.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.baekboom.backend.repository.memberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final memberRepository memberRepository;

    private UserDetails createUserDetails(MemberEntity member) {
        return User.builder()
                .username(member.getUsername())
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findById(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }
}