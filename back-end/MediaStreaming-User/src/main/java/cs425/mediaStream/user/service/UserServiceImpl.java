package cs425.mediaStream.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cs425.mediaStream.user.clientService.ChannelClientService;
import cs425.mediaStream.user.domain.Role;
import cs425.mediaStream.user.domain.User;
import cs425.mediaStream.user.repository.RoleRepository;
import cs425.mediaStream.user.repository.UserRepository;
import cs425.mediaStream.user.service.dto.ChannelDTO;
import cs425.mediaStream.user.service.dto.UserPage;
import cs425.mediaStream.user.service.dto.UserRequest;
import cs425.mediaStream.user.service.dto.UserResponse;
import cs425.mediaStream.user.util.JwtTokenUtil;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired 
	private RoleRepository roleRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private ChannelClientService channelClientService;

	@Override
	public Optional<UserResponse> getUser(long id) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			return Optional.ofNullable(null);
		return Optional.ofNullable(mapper.map(user.get(), UserResponse.class));
	}

	@Override
	public UserResponse createUser(UserRequest userRequest) {
		// TODO Auto-generated method stub
		User user = mapper.map(userRequest, User.class);
		user.setPassword(bcrypt.encode(user.getPassword()));
		Optional<Role> role = roleRepository.findById(2L);
		user.addRole(role.get());
		UserResponse newUser = mapper.map(userRepository.save(user),UserResponse.class);
		userRepository.flush();
		userRequest.setId(newUser.getId());
		channelClientService.createChannel(userRequest);
		return newUser;
	}

	@Override
	public UserResponse updateUser(UserRequest userRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

	@Override
	public Optional<UserResponse> banUnBanUser(long id,boolean ban) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			return Optional.ofNullable(null);
		user.get().setBanned(ban);
		return Optional.ofNullable(mapper.map(user.get(), UserResponse.class));
	}
	
	@Override
	public Optional<UserResponse> approveUserToStreamer(long id) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			return Optional.ofNullable(null);
		user.get().setApproved(true);
		return Optional.ofNullable(mapper.map(user.get(), UserResponse.class));
	}

	@Override
	public List<UserResponse> convertEntityListToResponseList(List<User> entityList) {
		// TODO Auto-generated method stub
		if(entityList ==null)
			return null;
		return entityList.stream()
				.map(entity -> mapper.map(entity, UserResponse.class))
				.collect(Collectors.toList());
	}
	
	public UserDetails valditate(String token) {
		String username = null;
		System.out.println("My token "+token);
			try {
				username = jwtTokenUtil.getUsernameFromToken(token);
			} catch (Exception e) {
				throw e;
			}
		if (username != null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			if (jwtTokenUtil.validateToken(token, userDetails)) {
				return userDetails;
			}
		}
		return null;
	}

	@Override
	public UserPage listUsers(int pageNum,int size) {
		// TODO Auto-generated method stub
		Pageable page = PageRequest.of(pageNum, size);
		List<UserResponse> users = convertEntityListToResponseList(userRepository.findAll(page).stream().collect(Collectors.toList()));
		return new UserPage(users, userRepository.getNumberOfUsers());
	}

}
