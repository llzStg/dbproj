package com.lz3258.final_jdbc.controller;

import com.lz3258.final_jdbc.dto.ItemDTO;
import com.lz3258.final_jdbc.dto.PieceLocationDTO;
import com.lz3258.final_jdbc.entity.Piece;
import com.lz3258.final_jdbc.service.ItemService;
import com.lz3258.final_jdbc.service.impl.ItemServiceImpl;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@AllArgsConstructor
public class ItemController {

    @Resource
    private ItemService itemService;

    @GetMapping("/available")
    public ResponseEntity<List<ItemDTO>> getAvailableItems(
            @RequestParam(required = false) String mainCategory,
            @RequestParam(required = false) String subCategory) {

        List<ItemDTO> availableItems = itemService.getAvailableItems(mainCategory, subCategory);
        return ResponseEntity.ok(availableItems);
    }

    @GetMapping("/{itemID}/pieces")
    public ResponseEntity<List<PieceLocationDTO>> getItemLocations(@PathVariable Integer itemID) {
        List<PieceLocationDTO> pieces = itemService.getLocationsByItemId(itemID);

        if (pieces.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(pieces);
    }
}
