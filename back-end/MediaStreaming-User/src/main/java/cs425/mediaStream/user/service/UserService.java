package cs425.mediaStream.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import cs425.mediaStream.user.domain.User;
import cs425.mediaStream.user.service.dto.UserPage;
import cs425.mediaStream.user.service.dto.UserRequest;
import cs425.mediaStream.user.service.dto.UserResponse;


public interface UserService {
	
	public Optional<UserResponse> getUser(long id);
	
	public UserPage listUsers(int pageNum,int size);
	
	public UserResponse createUser(UserRequest userRequest);
	
	public UserResponse updateUser(UserRequest userRequest);
	
	public void deleteUser(long id);

	public Optional<UserResponse>  banUnBanUser(long id,boolean ban);
	
	public Optional<UserResponse> approveUserToStreamer(long id);
	
	public UserDetails valditate(String token);
	
	List<UserResponse> convertEntityListToResponseList(List<User> entityList);
	
	
}
