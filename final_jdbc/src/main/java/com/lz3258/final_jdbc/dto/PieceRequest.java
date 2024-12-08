package com.lz3258.final_jdbc.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieceRequest {
    private Integer pieceNum;
    private String description;
    private Integer length;
    private Integer width;
    private Integer height;
    private Integer roomNum;
    private Integer shelfNum;
    private String notes;
}
