package com.lz3258.final_jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private String mainCategory;
    private String subCategory;
}
