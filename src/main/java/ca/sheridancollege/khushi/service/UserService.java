package ca.sheridancollege.khushi.service;

import ca.sheridancollege.khushi.bean.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.core.userdetails.UserDetailsService;

import ca.sheridancollege.khushi.bean.User;
import ca.sheridancollege.khushi.bean.UserRegistrationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);

	User findByEmail(String email);

	User findById(long id);

	List<Role> findRolesByUserId(long userId);

	void saveUsersRoles(Long userId, Long roleId);

	List<User> findAll();


}
