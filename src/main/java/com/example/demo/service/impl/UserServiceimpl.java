package com.example.demo.service.impl;

import com.example.demo.dao.IUserMapper;
import com.example.demo.dao.RoleMapper;
import com.example.demo.dao.SysPermissionMapper;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.LoginAppUser;
import com.example.demo.entity.SysPermission;
import com.example.demo.entity.SysRole;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private IUserMapper iUserDao;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<AppUser> findAll() {
        return iUserDao.findAll();
    }

    @Override
    public AppUser findByUserName(String userName){
        return iUserDao.findByUserName(userName);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginAppUser findByUsername(String username) {
        AppUser appUser = iUserDao.findByUserName(username);
        if (appUser != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(appUser, loginAppUser);

            Set<SysRole> sysRoles = roleMapper.findRolesByUserId(appUser.getId());
            // 设置角色
            loginAppUser.setSysRoles(sysRoles);

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Long> roleIds = sysRoles.parallelStream().map(SysRole::getId).collect(Collectors.toSet());
                Set<SysPermission> sysPermissions = sysPermissionMapper.findPermissionsByRoleIds(roleIds);
                if (!CollectionUtils.isEmpty(sysPermissions)) {
                    Set<String> permissions = sysPermissions.parallelStream().map(SysPermission::getPermission)
                            .collect(Collectors.toSet());
                    // 设置权限集合
                    loginAppUser.setPermissions(permissions);
                }

            }

            return loginAppUser;
        }

        return null;
    }
}