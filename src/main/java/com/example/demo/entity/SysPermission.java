package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限标识
 * @author lei
 * @date 2019/08/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission implements Serializable {

	private Long id;
	private String permission;
	private String name;
	private Date createTime;
	private Date updateTime;

}
