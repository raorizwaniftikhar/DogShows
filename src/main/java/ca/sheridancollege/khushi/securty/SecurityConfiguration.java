package ca.sheridancollege.khushi.securty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ca.sheridancollege.khushi.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(SecurityUtility.passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	private static final String[] PUBLIC_MATCHERS = { "/css/**", "/js/**", "/image/**", "/registration", "/login","/dog-shows/result","/h2-console/**","/console/**",
			"/dog-show/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().antMatchers("/user/**").hasRole("ADMIN")
				.anyRequest().authenticated();
		http.csrf().disable().cors().disable().formLogin().failureUrl("/login?error").loginPage("/login").permitAll()
				.and().logout().invalidateHttpSession(true) // invalidate session
				.clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login").deleteCookies("remember-me").permitAll().and().rememberMe()
				.key("aSecretKey").and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		http.headers().frameOptions().disable();


	}

}
