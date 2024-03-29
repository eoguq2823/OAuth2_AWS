package com.study.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	private int roleId;
	private String roleName;
}

