package com.example.demo.dao;

import com.example.demo.entity.AppUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMapper {

    List<AppUser> findAll();

    AppUser findByUserName(String userName);

}
