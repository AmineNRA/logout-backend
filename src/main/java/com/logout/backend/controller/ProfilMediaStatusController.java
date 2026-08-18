package com.logout.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logout.backend.configuration.JwtUtils;
import com.logout.backend.dto.ProfilMediaStatusDTO;
import com.logout.backend.enums.Status;
import com.logout.backend.service.ProfilMediaStatusService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/profilmediastatus")
public class ProfilMediaStatusController {

    private final ProfilMediaStatusService profilMediaStatusService;
    private final JwtUtils jwtUtils;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{id}")
    public List<ProfilMediaStatusDTO> getProfilMediaStatus(@PathVariable Integer id, @RequestParam Status status,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);
        return profilMediaStatusService.showProfilMediaStatus(profilId, status);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Void> createProfilMediaStatus(@Valid @RequestBody ProfilMediaStatusDTO profilMediaStatusDTO,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);

        profilMediaStatusService.createProfilMediaStatus(profilId, profilMediaStatusDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "{id}")
    public void updateProfilMediaStatus(@PathVariable Integer externalId,
            @Valid @RequestBody ProfilMediaStatusDTO profilMediaStatusDTO,
            @RequestHeader("Autorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);

        profilMediaStatusService.updateProfilMediaStatus(profilId, externalId, profilMediaStatusDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteProfilMediaStatus(@PathVariable Integer id) {
        profilMediaStatusService.deleteProfilMediaStatus(id);
    }
}
