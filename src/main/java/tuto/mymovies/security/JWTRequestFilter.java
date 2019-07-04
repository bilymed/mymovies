package tuto.mymovies.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Accepter tous les domains
		response.addHeader("Access-Control-Allow-Origin", "*");
		// Autoriser les entetes
		response.addHeader("Access-Control-Allow-Headers", "Origin, accept, X-Requested-With, content-type, "
				+ "Access-Control-Request-Method, " + "Access-Control-Request-Headers, " + "Authorization");
		
		// Exposer les entetes
		response.addHeader("Access-Control-Expose-Headers",
			    "Access-Control-Allow-Origin,"
			    + "Acess-Control-Allow-Credentials,Authorization");

		String jwtToken = request.getHeader(SecurityConstants.HEADER_STRING);
		
		//System.out.println("Token: " + jwtToken);
		
		// si la request est envoiye avec OPTIONS return status OK 
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			// recuperer token
			if (jwtToken == null || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}

			Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
					.parseClaimsJws(jwtToken.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody();
			String username = claims.getSubject();

			@SuppressWarnings("unchecked")
			ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			roles.forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role.get("authority")));
			});

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
					null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			filterChain.doFilter(request, response);
		}

	}

}
