package com.att.salesexpress.webapp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.att.salesexpress.webapp.entity.SalesUser;

@Service(value = "salesUserDetailsService")
public class SalesUserDetailsService implements UserDetailsService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("hikariOraJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private String sqlAuthUser = "select a.username, a.password, a.enabled, a.accountNonExpired, "
			+ "a.credentialsNonExpired, a.accountNonLocked, a.firstname, a.lastname, "
			+ "(select wm_concat(b.authority) from sales_authorities b where b.username=a.username) as authorities "
			+ "from SALES_USERS a where a.username=?";

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Inside loadUserByUsername() method");
		SalesUser salesUser = null;

		try {
			salesUser = jdbcTemplate.queryForObject(sqlAuthUser, new RowMapper<SalesUser>() {

				@Override
				public SalesUser mapRow(ResultSet rs, int i) throws SQLException {
					String username = rs.getString("username");
					String password = rs.getString("password");

					boolean enabled = "Y".equalsIgnoreCase(rs.getString("enabled"));
					boolean accountNonExpired = "Y".equalsIgnoreCase(rs.getString("accountNonExpired"));
					boolean credentialsNonExpired = "Y".equalsIgnoreCase(rs.getString("credentialsNonExpired"));
					boolean accountNonLocked = "Y".equalsIgnoreCase(rs.getString("accountNonLocked"));

					String firstname = rs.getString("firstname");
					String lastname = rs.getString("lastname");

					List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
					String strAuthorities = rs.getString("authorities");
					String[] authoritiesList = strAuthorities.split(",");
					for (String authority : authoritiesList) {
						authorities.add(new SimpleGrantedAuthority(authority));
					}

					return new SalesUser(username, password, enabled, accountNonExpired, credentialsNonExpired,
							accountNonLocked, authorities, firstname, lastname);
				}

			}, username);
		} catch (DataAccessException e) {
			logger.error("Exception occurred: " + ExceptionUtils.getStackTrace(e));
		}

		logger.info("User : " + salesUser);
		
		if (salesUser == null) {
			logger.debug("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		
		return salesUser;
	}
}

