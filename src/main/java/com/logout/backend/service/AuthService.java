package com.logout.backend.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.logout.backend.configuration.JwtUtils;
import com.logout.backend.model.Profil;
import com.logout.backend.repository.ProfilRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final ProfilRepository profilRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    public Profil createProfil(Profil profil) {
        if (profilRepository.existsByPseudo(profil.getPseudo())) {
            throw new IllegalArgumentException("Le pseudo est déjà utilisé");
        }
        if (profilRepository.existsByEmail(profil.getEmail())) {
            throw new IllegalArgumentException("L'email est déjà utilisé");
        }
        profil.setPassword(passwordEncoder.encode(profil.getPassword()));
        return profilRepository.save(profil);
    }

    public Map<String, Object> login(Profil profil) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(profil.getPseudo(), profil.getPassword()));
        if (authentication.isAuthenticated()) {
            Profil currentProfil = profilRepository.findByPseudo(profil.getPseudo())
                    .orElseThrow(() -> new UsernameNotFoundException("Profil introuvable"));
            Map<String, Object> authData = new HashMap<>();
            authData.put("id", currentProfil.getId());
            authData.put("token", jwtUtils.generateToken(currentProfil));
            authData.put("refreshToken", jwtUtils.generateRefreshToken(currentProfil));
            authData.put("type", "Bearer");
            return authData;
        }
        throw new IllegalArgumentException("Pseudo ou mot de passe incorrect");
    }

    public Map<String, Object> refreshToken(String refreshToken) {
        String pseudo = jwtUtils.extractUsername(refreshToken);
        if (pseudo != null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(pseudo);
            if (jwtUtils.validateToken(refreshToken, userDetails)) {
                Profil currentProfil = profilRepository.findByPseudo(pseudo)
                        .orElseThrow(() -> new UsernameNotFoundException("Profil introuvable"));

                String newAccessToken = jwtUtils.generateToken(currentProfil);

                Map<String, Object> authData = new HashMap<>();
                authData.put("token", newAccessToken);
                authData.put("type", "Bearer");
                return authData;
            }
        }
        throw new IllegalArgumentException("Token expiré");
    }

}
