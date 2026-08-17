package com.logout.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logout.backend.dto.ProfilDTO;
import com.logout.backend.service.ProfilService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/profil")
public class ProfilController {

    private final ProfilService profilService;

    @GetMapping
    public List<ProfilDTO> getAllProfil() {
        return profilService.getAllProfil();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "{id}")
    public void deleteProfil(Integer id) {
        profilService.deleteProfil(id);
    }

}
