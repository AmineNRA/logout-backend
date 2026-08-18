package com.logout.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logout.backend.configuration.JwtUtils;
import com.logout.backend.model.Profil;
import com.logout.backend.repository.ProfilRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ProfilRepository profilRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Profil profil) {
        if (profilRepository.findByPseudo(profil.getPseudo()) == null) {
            return ResponseEntity.badRequest().body("Username is already in use");
        }
        profil.setPassword(passwordEncoder.encode(profil.getPassword()));
        return ResponseEntity.ok(profilRepository.save(profil));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Profil profil) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(profil.getPseudo(), profil.getPassword()));
            if (authentication.isAuthenticated()) {
                Profil currentProfil = profilRepository.findByPseudo(profil.getPseudo())
                        .orElseThrow(() -> new UsernameNotFoundException("Profil introuvable"));
                Map<String, Object> authData = new HashMap<>();
                authData.put("token", jwtUtils.generateToken(currentProfil));
                authData.put("type", "Bearer");
                return ResponseEntity.ok(authData);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo ou mot de passe incorrect");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo ou mot de passe incorrect");
        }
    }
}
