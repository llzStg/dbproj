package com.lz3258.final_jdbc.entity;

import lombok.Data;

import java.sql.Date;
@Data
public class DonatedBy {

    private Integer itemID;

    private String userName;

    private Date donateDate;
}
