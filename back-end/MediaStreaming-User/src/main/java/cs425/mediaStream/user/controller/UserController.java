package cs425.mediaStream.user.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cs425.mediaStream.user.clientService.ChannelClientService;
import cs425.mediaStream.user.service.UserService;
import cs425.mediaStream.user.service.dto.AuthRequest;
import cs425.mediaStream.user.service.dto.ChannelDTO;
import cs425.mediaStream.user.service.dto.JwtTokenResponse;
import cs425.mediaStream.user.service.dto.UserPage;
import cs425.mediaStream.user.service.dto.UserRequest;
import cs425.mediaStream.user.service.dto.UserResponse;
import cs425.mediaStream.user.util.JwtTokenUtil;
import io.swagger.annotations.Api;

@Api(description  = "User contoller handles all end points related to user service and authentication ")
@RestController
@CrossOrigin//(exposedHeaders = {"Authorization"},allowedHeaders = "*")
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtUserDetailsService;
	
	@Autowired
	private ChannelClientService channelClientService;
	@GetMapping("/{id}")
	public UserResponse getUser(@PathVariable long id) {
		return userService.getUser(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User of id: "+ id +" not found"));
	}
	
	@PostMapping("/signup")
	public UserResponse createUser(@RequestBody UserRequest userRequest) {
		return userService.createUser(userRequest);
	}
	
	@DeleteMapping("{id}")
	public void deleteUser(@PathVariable long id) {
		userService.deleteUser(id);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("list")
	public UserPage getUserList(@RequestParam int pageNum,@RequestParam int size){
		return userService.listUsers(pageNum, size);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PatchMapping("/ban/{id}")
	public UserResponse banUser(@PathVariable long id,@RequestParam boolean ban) {
		return userService.banUnBanUser(id, ban)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User of id: "+ id +" not found"));
	}
	
	@PatchMapping("/approve/{id}")
	public UserResponse approveUser(@PathVariable long id) {
		return userService.approveUserToStreamer(id)
				.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User of id: "+ id +" not found"));
	}
	
	
	@PostMapping("${jwt.get.token.uri}")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);
		ChannelDTO channelDTO = channelClientService.getChannelIdByUser(token);
		return ResponseEntity.ok(new JwtTokenResponse(token,jwtTokenUtil.getExpiration(),channelDTO.getChannelImg(),channelDTO.getId(),channelDTO.getChannelName(),userDetails.getAuthorities()));
	}

	@PostMapping("/validate")
	public UserDetails validateToke(@RequestBody JwtTokenResponse jwtTokenResponse) {
		return userService.valditate(jwtTokenResponse.getToken());
	}
	
	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			//throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			//throw new AuthenticationException("INVALID_CREDENTIALS", e);
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad cer");
		}
	}
	
	
	
}
