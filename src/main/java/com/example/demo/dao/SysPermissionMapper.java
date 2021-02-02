package com.example.demo.dao;

import com.example.demo.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface SysPermissionMapper {

    Set<SysPermission> findPermissionsByRoleIds(Set<Long> ids);
}
