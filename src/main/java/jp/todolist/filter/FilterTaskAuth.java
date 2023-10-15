package jp.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.todolist.user.UserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks")) {
            // buscar usuário e senha
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                response.sendError(400, "Missing auth header");
                return;
            }
            String encodedAuthData = authHeader.substring("Basic".length()).trim();
            // validar usuário e senha
            byte[] decodedBytes = Base64.getDecoder().decode(encodedAuthData);

            String authCredentialString = new String(decodedBytes);

            String[] credentials = authCredentialString.split(":");

            String username = credentials[0];
            String password = credentials[1];

            var maybeUser = this.userRepository.findByUsername(username);

            if (maybeUser.isEmpty()) {
                response.sendError(401, "Usuário não cadastardo");
                return;
            }

            Result isCorrectPassword = BCrypt.verifyer().verify(password.toCharArray(), maybeUser.get().getPassword());

            if (!isCorrectPassword.verified) {
                response.sendError(401, "Senha incorreta");
                return;
            } else {
                // seguir
                request.setAttribute("user", maybeUser.get().getId());
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

}
