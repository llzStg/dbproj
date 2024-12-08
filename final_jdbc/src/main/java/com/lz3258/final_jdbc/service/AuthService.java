package com.lz3258.final_jdbc.service;

import com.lz3258.final_jdbc.dto.PersonRegisterRequest;
import com.lz3258.final_jdbc.entity.Person;
import com.lz3258.final_jdbc.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AuthService {

    public List<Role> getAllRoles();
    public boolean validateLogin(String userName, String rawPassword);

//    public boolean registerUser(Person person, String rawPassword);
    public boolean registerUser(PersonRegisterRequest personRegisterRequest);

    public String getRoleByUserName(String userName);


}
