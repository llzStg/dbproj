package com.lz3258.final_jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class AddItemRequest {
    private Integer itemId;
}
