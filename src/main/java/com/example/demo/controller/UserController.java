package com.example.demo.controller;

import com.example.demo.dao.RoleMapper;
import com.example.demo.dao.SysPermissionMapper;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.SysPermission;
import com.example.demo.entity.SysRole;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController("/test")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @GetMapping("/getAll")
    public List<AppUser> findAll(){
        return userService.findAll();
    }

    @GetMapping("/getRole")
    public Set<SysRole> findRolesByUserId(){
        return roleMapper.findRolesByUserId(1l);
    }

    @GetMapping("/getPermission")
    public Set<SysPermission> findPermissionsByRoleIds(){
        Set<Long> ids = new HashSet<>();
        ids.add(1l);
        return sysPermissionMapper.findPermissionsByRoleIds(ids);
    }
}
