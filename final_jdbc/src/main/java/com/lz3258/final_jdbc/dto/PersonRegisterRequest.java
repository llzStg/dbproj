package com.lz3258.final_jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonRegisterRequest {
    private String userName;
    private String password;
    private String fName;
    private String lName;
    private String email;
    private String roleID;
    private List<String> phones;

}
