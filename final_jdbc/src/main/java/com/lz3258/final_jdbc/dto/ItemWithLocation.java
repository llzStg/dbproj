package com.lz3258.final_jdbc.dto;

import com.lz3258.final_jdbc.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemWithLocation {

    private int itemId;
    private String description;
    private String color;
    private boolean isNew;
    private boolean hasPieces;
    private String material;
    private List<Location> locations;
}
