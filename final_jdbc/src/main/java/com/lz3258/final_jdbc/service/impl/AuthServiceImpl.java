package com.lz3258.final_jdbc.service.impl;

import com.lz3258.final_jdbc.dao.PersonDao;
import com.lz3258.final_jdbc.dto.PersonRegisterRequest;
import com.lz3258.final_jdbc.entity.Person;
import com.lz3258.final_jdbc.entity.Role;
import com.lz3258.final_jdbc.service.AuthService;
import com.lz3258.final_jdbc.util.MyJDBC;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private PersonDao personDao;

    @Override
    public List<Role> getAllRoles() {
        return personDao.getAllRoles();
    }

    @Override
    public boolean validateLogin(String userName, String rawPassword) {
        Person person = personDao.findByUsername(userName);
        if (person != null) {
            String storedPassword = person.getPassword();
            String salt = "SALT123";
            String hashedPassword = DigestUtils.md5DigestAsHex((salt + rawPassword).getBytes());
            return storedPassword.equals(hashedPassword);
        }
        return false;
    }

    @Override
    public boolean registerUser(PersonRegisterRequest personRegisterRequest) {
        if (personDao.isUsernameTaken(personRegisterRequest.getUserName())) {
            return false;
        }

        String salt = "SALT123";
        String hashedPassword = DigestUtils.md5DigestAsHex((salt + personRegisterRequest.getPassword()).getBytes());
        personRegisterRequest.setPassword(hashedPassword);
        return personDao.registerPerson(personRegisterRequest);
    }

    @Override
    public String getRoleByUserName(String userName) {
        return personDao.getRoleByUserName(userName);
    }

//    @Override
//    public boolean registerUser(Person person, String rawPassword) {
//        if (personDao.isUsernameTaken(person.getUserName())) {
//            return false;
//        }
//
//        String salt = "SALT123";
//        String hashedPassword = DigestUtils.md5DigestAsHex((salt + rawPassword).getBytes());
//        person.setPassword(hashedPassword);
//
//        return personDao.registerPerson(PersonRegisterRequest);
//    }


}
