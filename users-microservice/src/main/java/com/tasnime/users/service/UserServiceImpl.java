package com.tasnime.users.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tasnime.users.entities.Role;
import com.tasnime.users.entities.User;
import com.tasnime.users.repos.RoleRepository;
import com.tasnime.users.repos.UserRepository;

import jakarta.transaction.Transactional;
@Transactional
@Service

public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRep;
	@Autowired
	RoleRepository roleRep;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public User saveUser(User user) {
		// Set the default role 'USER'
        Role defaultRole = roleRep.findByRole("USER");
        user.setRoles(Collections.singletonList(defaultRole));
		user.setPassword(user.getPassword());
		return userRep.save(user);
	}

	@Override
	public User findUserByUsername(String username) {
		
		return userRep.findByUsername(username);
	}

	@Override
	public Role addRole(Role role) {
		
		return roleRep.save(role);
	}

	@Override
	public User addRoleToUser(String username, String rolename) {
		User usr = userRep.findByUsername(username);
		Role r = roleRep.findByRole(rolename);
		usr.getRoles().add(r);
		return usr;

	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRep.findAll();
	}

}
