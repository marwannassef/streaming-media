package cs425.mediaStream.user.service.dto;

import java.util.List;

public class UserPage {
	
	private List<UserResponse> users;
	private int size;

	public UserPage() {
	}
	public UserPage(List<UserResponse> users, int size) {
		this.users = users;
		this.size = size;
	}
	public List<UserResponse> getUsers() {
		return users;
	}
	public void setUsers(List<UserResponse> users) {
		this.users = users;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	

}
