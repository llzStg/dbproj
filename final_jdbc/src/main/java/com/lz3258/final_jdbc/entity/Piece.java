package com.lz3258.final_jdbc.entity;

import lombok.Data;

@Data
public class Piece {
    private Integer itemID;

    private Integer pieceNum;

    private String pDescriptiion;

    private Integer length;

    private Integer width;

    private Integer height;

    private Integer roomNum;

    private Integer shelfNum;

    private String pNotes;

}
