package com.example.demo.dao;

import com.example.demo.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface RoleMapper {

    Set<SysRole> findRolesByUserId(Long userId);
}
