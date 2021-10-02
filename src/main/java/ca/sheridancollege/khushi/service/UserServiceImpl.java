package ca.sheridancollege.khushi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.khushi.bean.Role;
import ca.sheridancollege.khushi.bean.User;
import ca.sheridancollege.khushi.bean.UserRegistrationDto;
import ca.sheridancollege.khushi.database.UserRepository;
import ca.sheridancollege.khushi.securty.SecurityUtility;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(null, registrationDto.getFirstName(), registrationDto.getLastName(),
				registrationDto.getEmail(), SecurityUtility.passwordEncoder().encode(registrationDto.getPassword()),
				registrationDto.getPicture(), null);
		return userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public List<Role> findRolesByUserId(long userId) {
		return userRepository.findRolesByUserId(userId);
	}

	@Override
	public void saveUsersRoles(Long userId, Long roleId) {
		userRepository.saveUsersRoles(userId,roleId);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities((List<Role>) user.getRoles()));
	}

	public Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		List<GrantedAuthority> listRole = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			listRole.add(new SimpleGrantedAuthority(role.getName()));
		}
		return listRole;
	}

}
