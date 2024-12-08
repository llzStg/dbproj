package com.lz3258.final_jdbc.entity;

import lombok.Data;

@Data
public class Item {
    private Integer itemID;

    private String iDescription;

    private String photo;

    private String color;

    private boolean isNew;

    private boolean hasPieces;

    private String material;

    private String mainCategory;

    private String subCategory;

}
