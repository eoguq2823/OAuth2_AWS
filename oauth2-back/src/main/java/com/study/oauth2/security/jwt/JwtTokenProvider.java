package com.study.oauth2.security.jwt;


import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.study.oauth2.security.PrincipalUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final Key key;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	public String generateAccessToken(Authentication authentication) {
		
		String email = null;

		if(authentication.getPrincipal().getClass() == UserDetails.class) {
			//일반로그인일때 (PrincipalUser
			PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
			email = principalUser.getEmail();
		} else {
			//oauth로그인일때 (OAuth2User)
			OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
			email = oAuth2User.getAttribute("email");
		}
		//권한설정
		if(authentication.getAuthorities() == null) {
			throw new RuntimeException("등록된 권한이 없습니다.");
		}
		StringBuilder roles = new StringBuilder();
		
		authentication.getAuthorities().forEach(authority -> {
			roles.append(authority.getAuthority() + ",");
		});
		roles.delete(roles.length() - 1, roles.length());
		
		Date toKenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
		
		return Jwts.builder()
				.setSubject("AccessToken")
				.claim("email", email)
				.claim("auth", roles)
				.setExpiration(toKenExpiresDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
				
	}
	
	public String generateOAuth2RegisterToken(Authentication authentication) {
		//util
		Date toKenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 10));
		
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		String email = oAuth2User.getAttribute("email");
		
		String registerToken = Jwts.builder()
								.setSubject("OAuth2Register")
								.claim("email", email)
								.setExpiration(toKenExpiresDate)
								.signWith(key, SignatureAlgorithm.HS256)
								.compact();
		
		return registerToken;
	}
	 public Boolean validateToken(String token) {
	      try {
	         Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(token);
	         return true;
	      }catch (Exception e) {
	      }
	      return false;
	   }
	 
	 public String getToken(String jwtToken) {
		 String type = "Bearer ";
		 if(StringUtils.hasText(jwtToken) && jwtToken.startsWith(type)) {
			 return jwtToken.substring(type.length());
		 }
		 return null;
	 }

}
