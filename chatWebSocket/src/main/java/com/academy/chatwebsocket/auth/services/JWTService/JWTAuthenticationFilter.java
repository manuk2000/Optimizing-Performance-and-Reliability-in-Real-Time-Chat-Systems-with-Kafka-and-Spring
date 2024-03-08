package com.academy.chatwebsocket.auth.services.JWTService;


import com.academy.chatwebsocket.auth.config.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    @Value("${constants.token-prefix}")
    private String TOKEN_PREFIX;
    @Value("${constants.refresh-token-api}")
    private String REFRESH_TOKEN_API;
    @Value("${constants.header-string}")
    private String HEADER_STRING;

    // This method is called for each incoming HTTP request.
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Check if the request path is for refreshing a token; if so, bypass this filter.
        if (request.getServletPath().equals(REFRESH_TOKEN_API)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the 'Authorization' header from the request.
        String authorization = request.getHeader(HEADER_STRING);

        // If there's no 'Authorization' header or it doesn't start with the token prefix, pass the request to the next filter.
        if (authorization == null || !authorization.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token from the 'Authorization' header (excluding the prefix).
        String token = authorization.substring(7);

        // Check if the token has expired.
        if (jwtService.isTokenExpired(token)) {
            // Retrieve the token expired message and set it in the response header.
            String tokenExpiredMessage = jwtService.getExpiredMessageFromToken(token);
            response.setHeader("error", tokenExpiredMessage);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            // Prepare a JSON response with the error message.
            Map<String, String> error = new HashMap<>();
            error.put("error", tokenExpiredMessage);
            response.setContentType(APPLICATION_JSON_VALUE);

            // Write the error response to the output stream.

            // Set the content type of the response
            response.setContentType("text/plain");

            // Get the PrintWriter object to write into the response
            PrintWriter out = response.getWriter();

            // Write your message into the response
            out.println(error);

            // Make sure to close the PrintWriter
            out.close();
            return;
        }

        // If the token is valid, extract the username from the token.
        final String username = jwtService.extractUsernameFromAccessToken(token);

        // If the username is not null and no authentication is currently set in the security context, set it.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

//         Continue processing the request with the updated security context.
        filterChain.doFilter(request, response);
    }
}
