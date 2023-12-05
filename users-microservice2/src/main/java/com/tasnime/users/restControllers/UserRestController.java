package com.tasnime.users.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tasnime.users.entities.Role;
import com.tasnime.users.entities.User;
import com.tasnime.users.service.UserService;
import com.tasnime.users.service.register.RegistationRequest;

@RestController
@CrossOrigin(origins = "*")//pour permettre lacces a cette api de nimporte quel site pour le tester par la suite

public class UserRestController {
	@Autowired //injection de depenadance
	UserService userService; //j'ai utilise userservice pour appeler la methode findallusers
	@RequestMapping(path = "all", method = RequestMethod.GET)

	public List<User> getAllUsers() {
		return userService.findAllUsers();
		}
	@PostMapping("/register")
	public User register(@RequestBody RegistationRequest request)
	{
	return userService.registerUser(request);
	}
	@GetMapping("/verifyEmail/{token}")
	 public User verifyEmail(@PathVariable("token") String token){ 
	return userService.validateToken(token);
	 }
	//gestion user
	 @RequestMapping(path = "addRole/{id}", method = RequestMethod.POST)
	    public User addRoleToUser(@PathVariable long id, @RequestBody Role r) {
	        return userService.addRoleToUserById(id, r);
	    }

	    @RequestMapping(path = "findUserById/{id}", method = RequestMethod.GET)
	    public User findUserById(@PathVariable Long id) {
	        return userService.findUserById(id);
	    }

	    @RequestMapping(path = "allRoles", method = RequestMethod.GET)
	    public List<Role> getAllRoles() {
	        return userService.findAllRoles();
	    }

	    @RequestMapping(path = "findRoleById/{id}", method = RequestMethod.GET)
	    public Role findRoleById(@PathVariable Long id) {
	        return userService.findRoleById(id);
	    }

	    @RequestMapping(path = "deleteUserById/{id}", method = RequestMethod.DELETE)
	    public void deleteUserById(@PathVariable long id) {
	        userService.deleteUser(id);
	    }

	    @RequestMapping(path = "removeRoleFromUer/{id}", method = RequestMethod.POST)
	    public User removeRole(@PathVariable long id, @RequestBody Role r) {
	        return userService.removeRoleFromUser(id, r);

	    }
}
