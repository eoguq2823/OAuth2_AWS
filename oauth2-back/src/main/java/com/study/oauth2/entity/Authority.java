package com.study.oauth2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data 
@NoArgsConstructor
@AllArgsConstructor
//권한
public class Authority {
	private int authorityId;
	private int userId;
	private int roleId;
	
	private Role role;
}
