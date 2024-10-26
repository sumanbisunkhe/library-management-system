package com.library.management.jwt;

import com.library.management.utils.CustomCustomerDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private CustomCustomerDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // Skip JWT validation for certain endpoints
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/users/register") || requestURI.startsWith("/api/auth/login")) {
            chain.doFilter(request, response); // Proceed without JWT processing
            return;
        }

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7); // Remove "Bearer " prefix
            try {
                username = jwtUtil.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
                sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Unable to get JWT Token");
                return;
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired");
                sendJsonErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
                return;
            }
        } else if (requestTokenHeader == null) {
            logger.warn("Authorization header is missing");
            sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Authorization header is missing");
            return;
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            sendJsonErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "JWT Token does not begin with Bearer String");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
                List<String> roles = jwtUtil.extractClaim(jwtToken, claims -> claims.get("roles", List.class));
                List<GrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

    private void sendJsonErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
    }
}
