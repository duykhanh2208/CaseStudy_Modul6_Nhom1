package com.houserenting.controller;

import com.houserenting.model.Role;
import com.houserenting.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService RoleServiceImpl;
    @GetMapping("")
    public ResponseEntity<Iterable<Role>>showAll(){
        return new ResponseEntity<>(RoleServiceImpl.findAll(), HttpStatus.OK);
    }
}
