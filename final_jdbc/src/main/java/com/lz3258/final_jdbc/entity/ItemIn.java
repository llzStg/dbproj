package com.lz3258.final_jdbc.entity;

import lombok.Data;

@Data
public class ItemIn {
    private Integer ItemID;

    private Integer orderID;

    private boolean found;
}
