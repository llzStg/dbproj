package com.lz3258.final_jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {
    private Integer itemId;
    private String description;
    private String photo;
    private String color;
    private boolean isNew;
    private boolean hasPieces;
    private String material;
    private String mainCategory;
    private String subCategory;
}
