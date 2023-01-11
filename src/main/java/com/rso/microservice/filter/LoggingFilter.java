package com.rso.microservice.filter;

import com.rso.microservice.util.MDCUtil;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class LoggingFilter implements Filter {

	final BuildProperties buildProperties;

	public LoggingFilter(BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;

		res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		res.setHeader("Access-Control-Max-Age", "8000");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization");

		String requestId = req.getHeader("X-Request-Id");
		if (requestId == null || requestId.isBlank())
			requestId = UUID.randomUUID().toString();

		MDCUtil.put(MDCUtil.MDCUtilKey.MICROSERVICE_NAME, "Data-Aggregation");
		MDCUtil.put(MDCUtil.MDCUtilKey.REQUEST_ID, requestId);
		MDCUtil.put(MDCUtil.MDCUtilKey.MICROSERVICE_VERSION, buildProperties.getVersion());
		filterChain.doFilter(servletRequest, servletResponse);
		MDCUtil.clear();
	}

}
