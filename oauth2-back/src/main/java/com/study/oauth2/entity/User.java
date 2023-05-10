package com.study.oauth2.entity;

import java.util.List;

import com.study.oauth2.security.PrincipalUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private int userId;
	private String email;
	private String password;
	private String name;
	private String provider;
	
	//하나의 유저는 여러개의 권한을 가질 수 있다.
	private List<Authority> authorities;
	
	public PrincipalUser toPrincipal() {
		return PrincipalUser.builder()
				.userId(userId)
				.email(email)
				.password(password)
				.authorities(authorities)
				.build();
	}
	
}
