package com.logout.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.logout.backend.dto.ListMediaItemDTO;
import com.logout.backend.mapper.ListMediaItemDTOMapper;
import com.logout.backend.model.ListMediaItem;
import com.logout.backend.repository.ListMediaItemRepository;
import com.logout.backend.repository.ListRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListMediaItemService {

    private final ListMediaItemRepository listMediaItemRepository;
    private final ListRepository listRepository;
    private final ListMediaItemDTOMapper listMediaItemDTOMapper;

    public List<Integer> getAllListMediaItemsOfList(Integer listId) {
        return listMediaItemRepository.findAllListMediaItemByListId(listId)
                .stream()
                .map(l -> l.getMediaId())
                .toList();
    }

    // Evite doublon
    public void createListMedia(Integer listMediaId, ListMediaItemDTO listMediaItemDTO) {
        com.logout.backend.model.List listProxy = listRepository.getReferenceById(listMediaId);
        ListMediaItem newListMediaItem = listMediaItemDTOMapper.tEntity(listMediaItemDTO);
        newListMediaItem.setList(listProxy);
        listMediaItemRepository.save(newListMediaItem);
    }

    public void deleteAllListMediaItemOfList(Integer listId) {
        List<ListMediaItem> listMediaItemToDelete = listMediaItemRepository.findAllListMediaItemByListId(listId);
        for (ListMediaItem listMediaItem : listMediaItemToDelete) {
            listMediaItemRepository.delete(listMediaItem);
        }
    }

    public void deleteListMediaItem(Integer id) {
        if (!listMediaItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Item de la liste introuvable avec l'id " + id);
        }
        listMediaItemRepository.deleteById(id);
    }
}
