package com.example.demo.controller;

import com.example.demo.dto.ItemDTO;
import com.example.demo.dto.RequestBodyDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.ItemEntity;
import com.example.demo.service.ItemService;
import org.apache.coyote.Response;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService service;


    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody ItemDTO dto) {
        try {
            String temporaryUserId = "yewonYoo";
            ItemEntity entity = ItemDTO.toEntity(dto);
            entity.setId(null);
            entity.setUserId(temporaryUserId);
            List<ItemEntity> entities = service.create(entity);
            List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);

        } catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);

        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveItemList() {
        String temporaryUserId = "yewonYoo";
        List<ItemEntity> entities = service.retrieve(temporaryUserId);
        List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());

        ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);

    }

    @PostMapping("/search")
    public ResponseEntity<?> searchItemByTitle(@RequestBody ItemDTO dto) {
        try {
            List<ItemEntity> entities = service.searchByTitle(dto.getTitle());
            List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateItem(@RequestBody ItemDTO dto) {
        String temporaryUserId = "yewonYoo";
        ItemEntity entity = ItemDTO.toEntity(dto);
        entity.setUserId(temporaryUserId);
        try {
            List<ItemEntity> updatedEntities = service.update(entity);
            if (updatedEntities.isEmpty()) {
                // 업데이트된 항목이 없는 경우
                return ResponseEntity.notFound().build();
            }
            List<ItemDTO> dtos = updatedEntities.stream().map(ItemDTO::new).collect(Collectors.toList());
            //List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteItem(@RequestBody ItemDTO dto) {
        try {
            String temporaryUserId = "yewonYoo";
            ItemEntity entity = ItemDTO.toEntity(dto);
            entity.setUserId(temporaryUserId);
            List<ItemEntity> entities = service.delete(entity);
            List<ItemDTO> dtos = entities.stream().map(ItemDTO::new).collect(Collectors.toList());
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<ItemDTO> response = ResponseDTO.<ItemDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
