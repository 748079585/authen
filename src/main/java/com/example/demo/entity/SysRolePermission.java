package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色与权限关系表
 * @author lei
 * @date 2019/08/16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysRolePermission {

	private Long id;
	
	private Long roleId;
	
	private Long permissionId;
}
