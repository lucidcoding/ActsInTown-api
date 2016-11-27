//package uk.co.luciditysoftware.actsintown.api.config;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.filter.GenericFilterBean;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureException;
//
//
////NO LONGER NEEDED?
//@Service
//public class JwtAutenticationFilter extends GenericFilterBean {
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
//			ServletException {
//		
//		HttpServletRequest httpRequest = (HttpServletRequest)request;
//		String authHeader = httpRequest.getHeader("Authorization");
//		
//		if (authHeader != null) {
//
//			String token = authHeader.substring(7);
//			
//			try {
//				
//				Claims claims = (Claims)Jwts.parser().setSigningKey(SecurityConfiguration.JWT_SIGNING_KEY).parse(token).getBody();
//				String username = claims.getSubject();
//				
//				SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(new UserPrincipal(){
//					private static final long serialVersionUID = 1L; 
//					{
//						this.setUsername(username);
//						this.setAuthorities(new ArrayList<CustomGrantedAuthority>());
//					}
//				}));
//
//			} catch (SignatureException e) {
//				//TODO: log attempt
//			}
//		}
//		
//		chain.doFilter(request, response); 
//        SecurityContextHolder.getContext().setAuthentication(null);
//	}
//}
