package com.att.salesexpress.webapp.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author sw088d
 *
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SkipLoginFilter implements Filter {

	/**
	 * 
	 * @param filterConfig
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		RequestHeaderWrapper requestWrapper = new RequestHeaderWrapper(req);
		String remote_addr = request.getRemoteAddr();
		requestWrapper.addHeader("remote_addr", remote_addr);
		harcodeLoginUser(request);
		chain.doFilter(requestWrapper, response);
	}

	/**
	 * 
	 * @param req
	 */
	private void harcodeLoginUser(ServletRequest req) {
		HttpServletRequest request = (HttpServletRequest) req;

		String user = request.getParameter("user");
		String passwd = request.getParameter("passwd");
		String reqFlowTxnId = request.getParameter("flow_txn_id");
		
		if (StringUtils.isNotEmpty(user) && StringUtils.isNotEmpty(passwd)) {
			HttpSession session = request.getSession(true);
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, passwd);
			Authentication authentication = authenticationManager.authenticate(authRequest);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
			if (StringUtils.isNotEmpty(reqFlowTxnId)) {
				session.setAttribute("flow_txn_id", reqFlowTxnId);
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;
}