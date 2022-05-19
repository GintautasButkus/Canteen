package lt.GintautasButkus.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lt.GintautasButkus.Dao.UserDao;
import lt.GintautasButkus.Entity.Client;
import lt.GintautasButkus.Entity.JwtRequest;
import lt.GintautasButkus.Entity.JwtResponse;
import lt.GintautasButkus.Util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) {
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		athenticate(userName, userPassword);
		
		final UserDetails userDetails = loadUserByUsername(userName);
		
		String newGeneratedToken = jwtUtil.generateToken(userDetails);
		
		Client client = userDao.findById(userName).get();
		
		return new JwtResponse(client, newGeneratedToken);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client client =  userDao.findById(username).get();
		if(client != null) {
			return new User(client.getUserName(),
					client.getUserPassword(),
					getAuthorities(client)
					);
		} else {
			throw new UsernameNotFoundException("Username is not valid");
		}
	}
	
	private void athenticate(String userName, String userPassword) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new DisabledException("User is disables");
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Bad credentials from user");
		}
		
	}
	
	private Set<SimpleGrantedAuthority> getAuthorities(Client client) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		client.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		
		return authorities;
	}
}
