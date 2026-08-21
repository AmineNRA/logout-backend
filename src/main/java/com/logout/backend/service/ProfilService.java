package com.logout.backend.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.logout.backend.dto.ProfilDTO;
import com.logout.backend.mapper.ProfilDTOMapper;
import com.logout.backend.model.Profil;
import com.logout.backend.repository.ProfilRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfilService {

    private final ProfilRepository profilRepository;
    private final ProfilDTOMapper profilDTOMapper;

    public List<ProfilDTO> getAllProfil() {
        return profilRepository.findAll()
                .stream()
                .map(profilDTOMapper::tDto)
                .toList();
    }

    public ProfilDTO getProfil(Integer id) {
        Profil profil = findProfil(id);
        return profilDTOMapper.tDto(profil);
    }

    private Profil findProfil(Integer id) {
        return profilRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profil introuvable avec l'id : " + id));
    }

    @Transactional
    public void updateProfil(Integer profilId, ProfilDTO profilDTO) {
        Profil findProfil = findProfil(profilId);
        if (!profilDTO.email().equals(findProfil.getEmail())
                && profilRepository.existsByEmail(profilDTO.email())) {
            throw new IllegalArgumentException("L'email existe déjà");
        }
        if (!profilDTO.pseudo().equals(findProfil.getPseudo())
                && profilRepository.existsByPseudo(profilDTO.pseudo())) {
            throw new IllegalArgumentException("Le pseudo existe déjà");
        }
        findProfil.setEmail(profilDTO.email());
        findProfil.setPseudo(profilDTO.pseudo());
        findProfil.setDescription(profilDTO.description());
        findProfil.setProfilPicture(profilDTO.profilPicture());
        profilRepository.save(findProfil);
    }

    public void deleteProfil(Integer id) {
        if (!profilRepository.existsById(id)) {
            throw new EntityNotFoundException("Client introuvable avec l'id : " + id);
        }
        profilRepository.deleteById(id);
    }
}
