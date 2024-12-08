package com.lz3258.final_jdbc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PieceLocationDTO {
    private Integer itemID;
    private String iDescription;
    private String photo;
    private String color;
    private boolean isNew;
    private boolean hasPieces;
    private String material;
    private String mainCategory;
    private String subCategory;

    private Integer pieceNum;
    private Integer roomNum;
    private Integer shelfNum;
    private String shelf;
    private String shelfDescription;
}
