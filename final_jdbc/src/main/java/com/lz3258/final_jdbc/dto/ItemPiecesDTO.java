package com.lz3258.final_jdbc.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ItemPiecesDTO {
    private Integer itemID; // 物品 ID
    private Integer pieceNum; // 分件编号
    private Integer roomNum; // 房间号
    private Integer shelfNum; // 货架号
    private String shelf; // 货架名称
    private String shelfDescription; // 货架描述
}
