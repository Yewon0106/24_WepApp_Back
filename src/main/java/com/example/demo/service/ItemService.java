package com.example.demo.service;

import com.example.demo.model.ItemEntity;
import com.example.demo.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;



    // 제품 등록
    public List<ItemEntity> create(final ItemEntity entity) {
        validate(entity);

        repository.save(entity);

        log.info("Entity id: {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    //retrieve
    public List<ItemEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<ItemEntity> searchByTitle(String title) {
        return repository.findByTitle(title);
    }

    public List<ItemEntity> update(final ItemEntity entity) {
        validate(entity);

        final Optional<ItemEntity> originalOpt = repository.findById(entity.getId());
        if (!originalOpt.isPresent()) {
            throw new EntityNotFoundException("Item not found with id: " + entity.getId());
        }

        ItemEntity original = originalOpt.get();
        // 제목 업데이트
        if (entity.getTitle() != null) {
            original.setTitle(entity.getTitle());
        }
        // 가격 업데이트
        if (entity.getPrice() != null) {
            original.setPrice(entity.getPrice());
        }
        // 카테고리 업데이트
        if (entity.getCategory() != null) {
            original.setCategory(entity.getCategory());
        }
        // 유저아이디 업데이트
        if (entity.getUserId() != null) {
            original.setUserId(entity.getUserId());
        }

        repository.save(original);
        //return List.of(repository.save(original));
        return retrieve(entity.getUserId());

    }

    public List<ItemEntity> delete(final ItemEntity entity)  {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity", entity.getId(), e);
            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieve(entity.getUserId());
    }


    private void validate(final ItemEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
