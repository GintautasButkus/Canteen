package lt.GintautasButkus.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lt.GintautasButkus.Dao.RoleDao;
import lt.GintautasButkus.Dao.UserDao;
import lt.GintautasButkus.Entity.Client;
import lt.GintautasButkus.Entity.Role;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Client registerNewUser(Client user) {
		Role role = roleDao.findById("user").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		return userDao.save(user);
	}
	
	public void initRolesAndUser() {
		Role adminRole = new Role();
		adminRole.setRoleName("admin");
		adminRole.setRoleDescription("Admin role.");
		roleDao.save(adminRole);
		
		Role userRole = new Role();
		userRole.setRoleName("user");
		userRole.setRoleDescription("Default role for newly created user.");
		roleDao.save(userRole);
		
		Client adminUser = new Client();
		adminUser.setUserFirstName("Gintautas");
		adminUser.setUserLastName("Butkus");
		adminUser.setUserName("admin");
		adminUser.setUserPassword(getEncodedPassword("admin@pass"));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRoles(adminRoles);
		userDao.save(adminUser);
		
//		Client user = new Client();
//		user.setUserFirstName("Rokas");
//		user.setUserLastName("Butkus");
//		user.setUserName("rokis");
//		user.setUserPassword(getEncodedPassword("rokis@pass"));
//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user.setRoles(userRoles);
//		userDao.save(user);
		
		
		
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

}
