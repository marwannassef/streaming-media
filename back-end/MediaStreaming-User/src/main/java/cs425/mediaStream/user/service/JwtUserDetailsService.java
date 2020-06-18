package cs425.mediaStream.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cs425.mediaStream.user.domain.Role;
import cs425.mediaStream.user.domain.User;
import cs425.mediaStream.user.repository.UserRepository;
import cs425.mediaStream.user.util.JwtUserDetails;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmailAndIsBannedFalse(email);
		List<SimpleGrantedAuthority> authorities = new ArrayList();
		for (Role role : user.getRoles()) {
			 authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return new JwtUserDetails(user.getId(),user.getEmail(), user.getPassword(), authorities);
	}
}
