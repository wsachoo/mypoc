package com.att.salesexpress.webapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier(value = "hikariOraDatasource")
	private DataSource dataSource;

	@Autowired
	private SalesAuthenticationSuccessHandler salesAuthenticationSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
		        .antMatchers("/", "/home", "/**").access("hasRole('USER')")
				.and().formLogin().loginPage("/login").successHandler(salesAuthenticationSuccessHandler).permitAll()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and().csrf()
				.and().exceptionHandling().accessDeniedPage("/accessDenied");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select LOGIN_ID as username, LOGIN_PWD as password, decode(ACTIVE_YN, 'Y', 1, 0) from FN_USER where LOGIN_PWD is not null and LOGIN_ID = ?")
				.authoritiesByUsernameQuery("select username, authority from sales_authorities where username = ? ");
	}
}
