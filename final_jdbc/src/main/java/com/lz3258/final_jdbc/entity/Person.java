package com.lz3258.final_jdbc.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    private String userName;

    private String password;

    private String fName;

    private String lName;

    private String email;
}
