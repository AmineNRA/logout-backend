package com.logout.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.logout.backend.dto.ListDTO;
import com.logout.backend.dto.ListMediaDetailsDTO;
import com.logout.backend.dto.ListMediaItemDTO;
import com.logout.backend.enums.Media;
import com.logout.backend.service.ListService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/listmedia")
public class ListController {

    private final ListService listService;
    private final JwtUtils jwtUtils;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<ListDTO> getListByMediaId(@RequestParam Media mediaType,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);
        return listService.getAllListOfProfilIdAndMediaType(profilId, mediaType);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "{id}")
    public ListMediaDetailsDTO getListWhithItems(@PathVariable Integer id) {
        System.out.println("Ca va dans service");
        return listService.getListWithItems(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createListMedia(@RequestBody ListDTO listDTO, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);
        listService.createListMedia(profilId, listDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{id}")
    public void addListMediaItems(@PathVariable Integer id, @RequestBody ListMediaItemDTO listMediaItemDTO) {
        listService.addListMediaItems(id, listMediaItemDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "{id}")
    public void updateListMediaName(@PathVariable Integer id, @RequestBody ListDTO listDTO,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer profilId = jwtUtils.extractProfilId(token);
        listService.updateListName(id, profilId, listDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}/items/{listMediaId}")
    public void deleteOneItemOfListMedia(@PathVariable Integer id, @PathVariable Integer listMediaId) {
        listService.deleteItemOfListMedia(id, listMediaId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{id}")
    public void deleteListMedia(@PathVariable Integer id) {
        listService.deleteListMedia(id);
    }

}
