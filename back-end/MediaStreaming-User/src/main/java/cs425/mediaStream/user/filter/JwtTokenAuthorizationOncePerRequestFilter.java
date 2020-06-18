package cs425.mediaStream.user.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cs425.mediaStream.user.config.ForwardToken;
import cs425.mediaStream.user.util.JwtTokenUtil;

@Component
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.http.request.header}")
	private String tokenHeader;
	
	@Autowired
	private ForwardToken forwardToken;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		logger.debug("Authentication Request For '{}'", request.getRequestURL());
		final String requestTokenHeader = request.getHeader(this.tokenHeader);
	
		String username = null;
		if (requestTokenHeader != null) {
			try {
				username = jwtTokenUtil.getUsernameFromToken(requestTokenHeader);
			} catch (Exception e) {
//				logger.error("JWT_Exception", e.getMessage());
				
			}
		}
		forwardToken.setToken(requestTokenHeader);
//		logger.debug("JWT_TOKEN_USERNAME_VALUE '{}'", username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			
			if (jwtTokenUtil.validateToken(requestTokenHeader, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

}