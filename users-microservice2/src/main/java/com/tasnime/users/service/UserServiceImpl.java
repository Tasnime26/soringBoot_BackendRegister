package com.tasnime.users.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tasnime.users.entities.Role;
import com.tasnime.users.entities.User;
import com.tasnime.users.repos.RoleRepository;
import com.tasnime.users.repos.UserRepository;
import com.tasnime.users.service.exceptions.EmailAlreadyExistsException;
import com.tasnime.users.service.exceptions.ExpiredTokenException;
import com.tasnime.users.service.exceptions.InvalidTokenException;
import com.tasnime.users.service.register.RegistationRequest;
import com.tasnime.users.service.register.VerificationToken;
import com.tasnime.users.service.register.VerificationTokenRepository;
import com.tasnime.users.util.EmailSender;

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
	@Autowired
	VerificationTokenRepository verificationTokenRepo;
	@Autowired
	EmailSender emailSender;
	@Override
	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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

	@Override
	public User registerUser(RegistationRequest request) {
		// je doit tout dabord verifier que l'email passer nexist pas et par la suite ajouter user 
		Optional<User> optionaluser = userRep.findByEmail(request.getEmail());
		if(optionaluser.isPresent())
			throw new EmailAlreadyExistsException("email déjà existant!");
		User newUser = new User();
		 newUser.setUsername(request.getUsername());
		 newUser.setEmail(request.getEmail());
		 
		newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
		 newUser.setEnabled(false);
		 userRep.save(newUser);
		//ajouter à newUser le role par défaut USER
		 Role r = roleRep.findByRole("USER"); //il va ramenr un objet de type role de name user 
		 List<Role> roles = new ArrayList<>();
		 roles.add(r);
		 newUser.setRoles(roles);
		//génére le code secret 
		 String code = this.generateCode();
		 VerificationToken token = new VerificationToken(code, newUser);
		 verificationTokenRepo.save(token);
		 sendEmailUser(newUser,token.getToken());
		 return userRep.save(newUser);
	}
	public String generateCode() {
		 Random random = new Random();
		 Integer code = 100000 + random.nextInt(900000); //genere un code aleatoire qui contient 6 chiffres 
		 
		 return code.toString();
		}
	public void sendEmailUser(User u, String code) {
		 String emailBody ="Bonjour "+ "<h1>"+u.getUsername() +"</h1>" +
		 " Votre code de validation est "+"<h1>"+code+"</h1>"; 
		emailSender.sendEmail(u.getEmail(), emailBody);
		}

	@Override
	public User validateToken(String code) {
		VerificationToken token = verificationTokenRepo.findByToken(code);
		 if(token == null){
		 throw new InvalidTokenException("Invalid Token");
		 }
		 
		 User user = token.getUser();
		 Calendar calendar = Calendar.getInstance();
		if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
		 verificationTokenRepo.delete(token);
		 throw new ExpiredTokenException("expired Token");
		 }
		 user.setEnabled(true);
		 userRep.save(user);
		 return user;}
	//gestion user
	 @Override
	    public void deleteUser(long id) {
	        userRep.deleteById(id);
	    }
	 @Override
	    public User removeRoleFromUser(long id, Role r) {
	        User user=userRep.findUserById(id);
	        List<Role> listOfrole=user.getRoles();

	        listOfrole.remove(r);
	        userRep.save(user);
	        return user;
	    }
	 @Override
	    public Role findRoleById(Long id) {
	        return roleRep.findRoleById(id);
	    }

	    @Override
	    public User findUserById(Long id) {
	        return userRep.findById(id).get();
	    }

		@Override
		public User addRoleToUserById(long id, Role r) {
			User usr = userRep.findUserById(id);

	        List<Role> roles = usr.getRoles();
	        roles.add(r);

	        usr.setRoles(roles);

	        return userRep.save(usr);
		}
		 @Override
		    public List<Role> findAllRoles() {
		        return roleRep.findAll();
		    }
	  

	  
}
