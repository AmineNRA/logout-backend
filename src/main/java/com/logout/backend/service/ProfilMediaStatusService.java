package com.logout.backend.service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.logout.backend.dto.ProfilMediaStatusDTO;
import com.logout.backend.enums.Status;
import com.logout.backend.mapper.ProfilMediaStatusDTOMapper;
import com.logout.backend.model.Profil;
import com.logout.backend.model.ProfilMediaStatus;
import com.logout.backend.repository.ProfilMediaStatusRepository;
import com.logout.backend.repository.ProfilRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfilMediaStatusService {

    private final ProfilMediaStatusRepository profilMediaStatusRepository;
    private final ProfilMediaStatusDTOMapper profilMediaStatusDTOMapper;
    private final ProfilRepository profilRepository;

    public void createProfilMediaStatus(Integer profilId, ProfilMediaStatusDTO profilMediaStatusDTO) {

        if (profilMediaStatusRepository.existsByMediaIdAndProfilIdAndMediaType(profilMediaStatusDTO.mediaId(), profilId,
                profilMediaStatusDTO.mediaType())) {
            throw new IllegalArgumentException("Le media est déjà dans votre liste");
        }

        Profil profilProxy = profilRepository.getReferenceById(profilId);

        ProfilMediaStatus profilMediaStatus = profilMediaStatusDTOMapper.tEntity(profilMediaStatusDTO);

        profilMediaStatus.setProfil(profilProxy);
        profilMediaStatusRepository.save(profilMediaStatus);
    }

    public List<ProfilMediaStatusDTO> showProfilMediaStatus(Integer profilId, Status status) {
        return profilMediaStatusRepository.findByProfilIdAndStatus(profilId, status)
                .stream()
                .map(profilMediaStatusDTOMapper::tDto)
                .toList();
    }

    @Transactional
    public void updateProfilMediaStatus(Integer profilId, Integer id,
            ProfilMediaStatusDTO profilMediaStatusDTO) {
        ProfilMediaStatus profilMediaStatusToUpdate = profilMediaStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statut introuvable"));

        if (!profilMediaStatusToUpdate.getProfil().getId().equals(profilId)) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à modifier cet élément");
        }

        profilMediaStatusToUpdate.setStatus(profilMediaStatusDTO.status());
    }

    public void deleteProfilMediaStatus(Integer id) {
        if (!profilMediaStatusRepository.existsById(id)) {
            throw new EntityNotFoundException("Le média est introuvable avec l'id " + id);
        }
        profilMediaStatusRepository.deleteById(id);
    }
}
