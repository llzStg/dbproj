package com.lz3258.final_jdbc.service;

import com.lz3258.final_jdbc.dto.ItemDTO;
import com.lz3258.final_jdbc.dto.PieceLocationDTO;

import java.util.List;

public interface ItemService {

    public List<ItemDTO> getAvailableItems(String mainCategory, String subCategory);


    public List<PieceLocationDTO> getLocationsByItemId(Integer itemID);
}
