package service;

import entity.User;

public class GlobalService {
	
	private static GlobalService instance;
	
	private User loggedInUser;
	
	public User getLoggedInUser() {
		return this.loggedInUser;
	}
	
	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
	public static GlobalService getInstance() {
		if(GlobalService.instance == null) {
			GlobalService.instance = new GlobalService();
		}
		return GlobalService.instance;
	}
}
