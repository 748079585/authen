package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户与角色关系表
 * @author lei
 * @date 2019/08/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysRoleUser {
	
	private Long id;
	
	private Long userId;
	
	private Long roleId;
	
}
