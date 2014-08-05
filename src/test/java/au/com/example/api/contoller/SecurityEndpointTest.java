package au.com.example.api.contoller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityEndpointTest {
	
	protected void login(String username, String password) {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password));    
	}
	
	protected void logout() {
		SecurityContextHolder.clearContext();
	}
}
