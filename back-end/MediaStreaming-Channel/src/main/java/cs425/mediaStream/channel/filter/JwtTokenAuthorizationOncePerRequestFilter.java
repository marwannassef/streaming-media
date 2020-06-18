package cs425.mediaStream.channel.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cs425.mediaStream.channel.clientService.UserClientService;
import cs425.mediaStream.channel.config.ForwardToken;


@Component
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Autowired
	private UserClientService userClientService;
	
	@Autowired
	private ForwardToken forwardToke;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		logger.debug("Authentication Request For '{}'", request.getRequestURL());
		
		final String requestTokenHeader = request.getHeader(this.tokenHeader);
		System.out.println("My Token : "+requestTokenHeader);
		Optional<UserDetails> userDetails =  userClientService.validate(requestTokenHeader);
	
		if (userDetails.isPresent()) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails.get(), null, userDetails.get().getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			forwardToke.setToken(requestTokenHeader);
		}
		chain.doFilter(request, response);
	}

}