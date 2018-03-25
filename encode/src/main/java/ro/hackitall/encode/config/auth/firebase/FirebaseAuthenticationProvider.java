package ro.hackitall.encode.config.auth.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import ro.hackitall.encode.service.UserService;
import ro.hackitall.encode.service.exception.FirebaseUserNotExistsException;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userService;

	public boolean supports(Class<?> authentication) {
		return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}

		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();

		FirebaseAuthenticationToken authenticationToken = (FirebaseAuthenticationToken) authentication;
/*		UserDetails details = userService.loadUserByUsername(authenticationToken.getName());
		if (details == null) {
			throw new FirebaseUserNotExistsException();
		}

		authenticationToken = new FirebaseAuthenticationToken(details, authentication.getCredentials(),
				details.getAuthorities());*/

		return authenticationToken;
	}

}
