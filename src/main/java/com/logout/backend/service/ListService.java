package com.logout.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.logout.backend.dto.ListDTO;
import com.logout.backend.dto.ListMediaDetailsDTO;
import com.logout.backend.dto.ListMediaItemDTO;
import com.logout.backend.enums.Media;
import com.logout.backend.mapper.ListDTOMapper;
import com.logout.backend.model.Profil;
import com.logout.backend.repository.ListRepository;
import com.logout.backend.repository.ProfilRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ListRepository listRepository;
    private final ListDTOMapper listDTOMapper;
    private final ListMediaItemService listMediaItemService;
    private final ProfilRepository profilRepository;

    // Je veux créer une liste --- Ok --- Test Ok
    // Je veux ajouter un item à une liste --- Ok --- Test Ok
    // Je veux afficher toutes les listes relié par l'id de l'utilisateur et le type
    // de média --- Ok --- Test Ok
    // Je veux afficher une liste avec tous les items --- Ok --- Test Ok
    // Je veux modifier le nom d'une liste --- Ok --- Test Ok
    // Je veux supprimer un item de la liste --- Ok --- Test Ok
    // Je veux supprimer une liste avec tous les items de la liste --- Ok --- Test
    // Ok

    public List<ListDTO> getAllListOfProfilIdAndMediaType(Integer profilId, Media mediaType) {
        if (!profilRepository.existsById(profilId)) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à accéder aux listes");
        }
        return listRepository.findAllListByProfilIdAndMediaType(profilId, mediaType)
                .stream()
                .map(listDTOMapper::tDto)
                .toList();
    }

    public ListMediaDetailsDTO getListWithItems(Integer id) {
        com.logout.backend.model.List list = listRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La liste avec l'id " + id + " n'existe pas"));
        List<Integer> listMediaItem = listMediaItemService.getAllListMediaItemsOfList(id);
        return new ListMediaDetailsDTO(list.getName(), listMediaItem);
    }

    public void createListMedia(Integer profilId, ListDTO listDTO) {
        Profil profilProxy = profilRepository.getReferenceById(profilId);
        com.logout.backend.model.List newList = listDTOMapper.tEntity(listDTO);
        newList.setProfil(profilProxy);
        listRepository.save(newList);
    }

    public void addListMediaItems(Integer id, ListMediaItemDTO listMediaItemsDTO) {
        listMediaItemService.createListMedia(id, listMediaItemsDTO);
    }

    @Transactional
    public void updateListName(Integer id, Integer profilId, ListDTO listDTO) {
        com.logout.backend.model.List listMediaToUpdate = listRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La liste avec l'id " + id + " n'existe pas"));
        if (!listMediaToUpdate.getProfil().getId().equals(profilId)) {
            throw new IllegalArgumentException("Vous n'êtes pas autorisé à modifier cette liste");
        }
        listMediaToUpdate.setName(listDTO.name());
        listRepository.save(listMediaToUpdate);
    }

    public void deleteItemOfListMedia(Integer id, Integer listMediaItemsId) {
        if (!listRepository.existsById(id)) {
            throw new EntityNotFoundException("La liste avec l'id " + id + " n'existe pas");
        }
        listMediaItemService.deleteListMediaItem(listMediaItemsId);
    }

    public void deleteListMedia(Integer id) {
        if (!listRepository.existsById(id)) {
            throw new EntityNotFoundException("La liste avec l'id " + id + " n'existe pas");
        }
        listMediaItemService.deleteAllListMediaItemOfList(id);
        listRepository.deleteById(id);
    }

}
