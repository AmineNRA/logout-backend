package com.logout.backend.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.logout.backend.model.Profil;
import com.logout.backend.repository.ProfilRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ProfilRepository profilRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profil profil = profilRepository.findByPseudo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Profil introuvable avec le pseudo : " + username));
        return User.builder()
                .username(profil.getPseudo())
                .password(profil.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
