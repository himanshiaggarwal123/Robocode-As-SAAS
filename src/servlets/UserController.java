package servlets;

import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;


import org.apache.http.HttpStatus;

import servlets.User;
import servlets.UserRescources;

public class UserController {
	UserRescources userRescource = new UserRescources();
	public boolean isUserExist(String username){
		User user = userRescource.findUser(User.class, username);
		if(user.getUsername() == null){
			return false;
		}
		return true;
	}
	public boolean editUser(User user, String username){
		Response response = userRescource.editUser(user, username);
		if(response.getStatus() == HttpStatus.SC_OK){
			return true;
		}
		return false;
	}
	public List<User> getAllUser(){
		GenericType<List<User>> gList = new GenericType<List<User>>(){};
		List<User> userList = (List<User>) userRescource.getAllUsers(gList);
		return userList;
	}
	public User createUser(User user){
		User newUser = (User)userRescource.createUser(user, User.class);
		return newUser;
	}
}
