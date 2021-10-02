package ca.sheridancollege.khushi.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.khushi.bean.Role;
import ca.sheridancollege.khushi.bean.User;

@Repository
public class UserRepository {
	@Autowired
	private NamedParameterJdbcTemplate dataSource;

	public User findByEmail(String email) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		User user = new User();
		// Provide email parameter to the query
		String query = "select * from user where email = :email";
		parameters.addValue("email", email);
		// Return the list of all matching users
		List<User> users = (ArrayList<User>) dataSource.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0) {
			user = users.get(0);
			user.setRoles(findRolesByUserId(user.getId()));
		} else{
			user = new User();
		}
		return user;
	}
	public User findById(long id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		User user = new User();
		// Provide email parameter to the query
		String query = "select * from user where id = :id";
		parameters.addValue("id", id);
		// Return the list of all matching users
		List<User> users = (ArrayList<User>) dataSource.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if (users.size() > 0) {
			user = users.get(0);
			user.setRoles(findRolesByUserId(user.getId()));
		} else
			user = new User();
		return user;
	}

	public List<Role> findRolesByUserId(long userId) {
		ArrayList<Role> roles = new ArrayList<Role>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select users_roles.user_id, role.name , role.id " + "FROM users_roles, role "
				+ "WHERE users_roles.role_id = role.id " + "AND user_id = :user_id";
		parameters.addValue("user_id", userId);

		List<Map<String, Object>> rows = dataSource.queryForList(query, parameters);

		// Extract role names and return them
		for (Map<String, Object> row : rows) {
			Role role = new Role();
			role.setName((String) row.get("name"));
			role.setId((Long) row.get("id"));
			roles.add(role);
		}
		return roles;
	}

	public User save(User user) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		// Provide email/password as parameter
		String query = "INSERT INTO user " + "(email, first_name, last_name, password , image_link) "
				+ "VALUES (:email, :first_name, :last_name, :password , :image_link)";
		parameters.addValue("first_name", user.getFirstName());
		parameters.addValue("last_name", user.getLastName());
		parameters.addValue("email", user.getEmail());
		parameters.addValue("password", user.getPassword());
		parameters.addValue("image_link", user.getPicture());
		dataSource.update(query, parameters);
		user = findByEmail(user.getEmail());
		MapSqlParameterSource param = new MapSqlParameterSource();
		Role role = new Role();
		// Provide email parameter to the query
		String roleQuery = "select * from role where name = :name";
		param.addValue("name", "ROLE_USER");
		List<Role> roles = (ArrayList<Role>) dataSource.query(roleQuery, param,
				new BeanPropertyRowMapper<Role>(Role.class));
		role = roles.get(0);
		saveUsersRoles(user.getId(), role.getId());
		return user;

	}

	// Method to associate user with role(s)
	public void saveUsersRoles(Long userId, Long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		// Provide parameters
		String query = "INSERT INTO users_roles (user_id, role_id)" + "values (:user_id, :role_id)";
		parameters.addValue("user_id", userId);
		parameters.addValue("role_id", roleId);
		// Insert a row to user_role table to reflect the association
		dataSource.update(query, parameters);

	}
 public List<User> findAll(){
	 	String query = "select * from user";
	 // Return the list of all users
	 	List<User> users = (ArrayList<User>) dataSource.query(query, new BeanPropertyRowMapper<User>(User.class));
	 	if (users != null && users.size() > 0) {
			for (int user = 0; user < users.size(); user++) {
				users.get(user).setRoles(findRolesByUserId(users.get(user).getId()));
			}
	 	}
		return users;
 }
}
