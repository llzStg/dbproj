package com.lz3258.final_jdbc.dto;

import lombok.*;

import java.sql.Date;
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private int orderId;
    private Date orderDate;
    private String orderNotes;
    private String supervisor;
    private String client;
}
