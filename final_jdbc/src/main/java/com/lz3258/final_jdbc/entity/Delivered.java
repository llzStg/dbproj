package com.lz3258.final_jdbc.entity;

import lombok.Data;

import java.sql.Date;
@Data
public class Delivered {
    private String userName;

    private Integer orderId;

    private String status;

    private Date date;
}
