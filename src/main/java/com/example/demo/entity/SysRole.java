package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 * @author lei
 * @date 2019/08/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysRole implements Serializable {

	private Long id;
	private String code;
	private String name;
	private Date createTime;
	private Date updateTime;
}
