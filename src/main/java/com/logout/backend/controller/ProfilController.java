package com.logout.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logout.backend.configuration.JwtUtils;
import com.logout.backend.dto.ProfilDTO;
import com.logout.backend.service.ProfilService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/profil")
public class ProfilController {

    private final ProfilService profilService;
    private final JwtUtils jwtUtils;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProfilDTO> getAllProfil() {
        return profilService.getAllProfil();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{id}")
    public ProfilDTO getProfil(@PathVariable Integer id) {
        return profilService.getProfil(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/me")
    public ProfilDTO getMineProfil(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);
        return profilService.getProfil(profilId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "{id}")
    public void updateProfil(@PathVariable Integer id, @RequestBody @Valid ProfilDTO profilDTO) {
        profilService.updateProfil(id, profilDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteProfil(@PathVariable Integer id) {
        profilService.deleteProfil(id);
    }

}
