package com.moa.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.moa.admin.user.UserRepository;
import com.moa.common.entity.User;

public class MealkitUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.getUserByEmail(email);
		if (user == null) throw new UsernameNotFoundException("없음" + email);
		return new MealkitUserDetails(user);
	}

}
