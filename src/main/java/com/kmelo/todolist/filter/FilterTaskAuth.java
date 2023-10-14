package com.kmelo.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.kmelo.todolist.model.entities.UserEntity;
import com.kmelo.todolist.model.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        if(servletPath.startsWith("/tasks/")) {
            String authorization = request.getHeader("Authorization");

            String authorization_pass = authorization.substring("Basic".length()).trim();

            byte[] pass_Decoded = Base64.getDecoder().decode(authorization_pass);
            String user_pass = new String(pass_Decoded);

            String[] credentials = user_pass.split(":");
            String user = credentials[0];
            String password = credentials[1];

            UserEntity actualUser = this.userRepository.findByUsername(user);

            if (actualUser == null) {
                response.sendError(401);
            } else {
                BCrypt.Result verifyPass = BCrypt.verifyer().verify(password.toCharArray(), actualUser.getPassword());
                if(verifyPass.verified) {
                    request.setAttribute("idUser", actualUser.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }
}
