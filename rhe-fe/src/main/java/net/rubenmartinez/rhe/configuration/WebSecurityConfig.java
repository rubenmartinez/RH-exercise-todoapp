package net.rubenmartinez.rhe.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import net.rubenmartinez.rhe.user.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String JSESSIONID = "JSESSIONID";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/lib/**").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/actuator/health").permitAll()
			// XXX .antMatchers("/built/**//**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/", true)
			.permitAll()
			.and()
			.httpBasic()
			.and()
			.csrf().disable().headers().frameOptions().disable().and()
			.logout()
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true)
			.deleteCookies(JSESSIONID);
	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserService userService) throws Exception {
        auth.userDetailsService(userService);
    }
	
}
