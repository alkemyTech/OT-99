package com.alkemy.ong.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.alkemy.ong.model.Users;
import com.alkemy.ong.service.JwtTokenUtil;
import com.alkemy.ong.service.UserService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokeUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		String email = null;
		String jwt = null;
		Long id = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

			jwt = authorizationHeader.substring(7);

			email = jwtTokeUtil.extractUsername(jwt); 
			
			id = Long.valueOf(jwtTokeUtil.extractId(jwt));	

		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			Users user = userService.findByEmail(email);

			if (id==user.getId() || user.getRol().getName().equalsIgnoreCase("ADMIN")) {
			
				Collection<GrantedAuthority> roles=new ArrayList<>();
				roles.add(new SimpleGrantedAuthority(user.getRol().getName()));
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
					new UsernamePasswordAuthenticationToken(user, null,roles);

				usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().
						buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
				
			}
		}

		filterChain.doFilter(request, response);

	}

}
