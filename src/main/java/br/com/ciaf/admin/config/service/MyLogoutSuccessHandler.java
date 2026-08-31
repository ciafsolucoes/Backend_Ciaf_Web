package br.com.ciaf.admin.config.service;

import br.com.ciaf.admin.config.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    private TokenProvider jwtTokenUtil;
    @Autowired
    private void setTokenProvider(TokenProvider tokenProvider){
        this.jwtTokenUtil = tokenProvider;
    }

    @Autowired
    ActiveUserStore activeUserStore;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String token = jwtTokenUtil.resolveToken(request);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (session != null){
            session.removeAttribute(username);
            activeUserStore.removeSpecificUser(username);
        }
    }
}
