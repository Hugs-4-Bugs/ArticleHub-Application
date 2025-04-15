package com.article.hub.Filter;

import com.article.hub.JWT.JwtService;
import com.article.hub.JWT.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userDetailsService;  // from JWT package


    /** skip the validation for public endpoints ie sign up, login, or getAllPublishedArticle will be open for all users

    Arrays.asList("/appuser/addNewAppUser/",
            "/appuser/login", "/article/getAllPublishedArticle")
    */
    private final List<String> allowedEndPoints = Arrays.asList(
            "/appUser/addNewAppUser",
            "/appUser/login",
            "/appuser/addNewAppUser",
            "/appuser/login",
            "/article/getAllPublishedArticle"
    );


    String email = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;


        if (allowedEndPoints.contains(request.getRequestURI())) {  // this is the case we don't want to filter the above, mentioned endpoints
            filterChain.doFilter(request, response);
            return;
        }

        // here we are extracting the values 👇
        if (authHeader != null && authHeader.startsWith("Bearer ")) {     // total 7 character including 1 space as [Bearer ]
            token = authHeader.substring(7);  // count of character "Bearer " with 1 space
            email = jwtService.extractUsername(token);   // extractUsername is initialized in JwtService class
        }

        // here 👇 now we have to check for the values if they are valid or not and also we have to check for the session for the user
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);   // this is the username which we have extracted from token
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // now the validation is done and afterthat we'll do filterChain().doFilter() and let the user pass and access out API and get the response
        filterChain.doFilter(request, response);
    }

    public String getEmail() {
        return email;
    }

}
