package com.example.demo.dto;

import com.example.demo.model.ItemEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
    private String id;
    private String title;
    private String category;
    private String price;
    private String userId;


    public ItemDTO(final ItemEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.price = entity.getPrice();
        this.userId = entity.getUserId();
    }
    public static ItemEntity toEntity(final ItemDTO dto) {
        return ItemEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .userId(dto.getUserId())
                .build();
    }
}
