package com.tasnime.users.service;

import java.util.List;

import com.tasnime.users.entities.Role;
import com.tasnime.users.entities.User;
import com.tasnime.users.service.register.RegistationRequest;

public interface UserService {
User saveUser(User user);
User findUserByUsername (String username);
Role addRole(Role role);
User addRoleToUser(String username, String rolename);
List <User> findAllUsers ();
User registerUser(RegistationRequest request);
public void sendEmailUser(User u, String code);
public User validateToken(String code);
//gestion user
void deleteUser(long id);
User removeRoleFromUser(long id, Role r);
Role findRoleById(Long id);
User findUserById(Long id);
User addRoleToUserById(long id, Role r);
List<Role> findAllRoles();
}