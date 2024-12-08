package com.lz3258.final_jdbc.entity;

import lombok.*;

import java.sql.Date;
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ordered {
    private Integer orderID;

    private Date orderDate;

    private String orderNotes;

    private String supervisor;

    private String client;


}
