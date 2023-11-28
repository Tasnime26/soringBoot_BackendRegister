package com.tasnime.users.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tasnime.users.entities.User;
import com.tasnime.users.service.UserService;

@RestController
@CrossOrigin(origins = "*")//pour permettre lacces a cette api de nimporte quel site pour le tester par la suite

public class UserRestController {
	@Autowired //injection de depenadance
	UserService userService; //j'ai utilise userservice pour appeler la methode findallusers
	@RequestMapping(path = "all", method = RequestMethod.GET)

	public List<User> getAllUsers() {
		return userService.findAllUsers();
		}
	 @RequestMapping(path = "add", method = RequestMethod.POST)
	    public User saveUser(@RequestBody User user) {
	        return userService.saveUser(user);
	    }
	 
	 
}
