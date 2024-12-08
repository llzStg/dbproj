package com.lz3258.final_jdbc.service.impl;

import com.lz3258.final_jdbc.dao.ItemDao;
import com.lz3258.final_jdbc.dto.ItemDTO;
import com.lz3258.final_jdbc.dto.PieceLocationDTO;
import com.lz3258.final_jdbc.service.ItemService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    @Resource
    private ItemDao itemDao;

    @Override
    public List<ItemDTO> getAvailableItems(String mainCategory, String subCategory) {
        return itemDao.findAvailableItems(mainCategory, subCategory);
    }

    @Override
    public List<PieceLocationDTO> getLocationsByItemId(Integer itemID) {
        return itemDao.findLocationByItemId(itemID);
    }
}
