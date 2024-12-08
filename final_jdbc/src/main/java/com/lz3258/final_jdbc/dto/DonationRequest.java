package com.lz3258.final_jdbc.dto;

import com.lz3258.final_jdbc.entity.Item;
import lombok.*;

import java.util.Date;
import java.util.List;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequest {
    private String description;
    private String photo;
    private String color;
    private boolean isNew;
    private boolean hasPieces;
    private String material;
    private String mainCategory;
    private String subCategory;
    private String categoryNotes; // 分类说明
    private Date donateDate;
    private List<PieceRequest> pieces; // 分件信息

}
