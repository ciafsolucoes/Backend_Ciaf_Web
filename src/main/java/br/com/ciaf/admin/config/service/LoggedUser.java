package br.com.ciaf.admin.config.service;

import br.com.ciaf.admin.config.TokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.io.Serializable;
import java.util.List;

public class LoggedUser implements HttpSessionBindingListener, Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private ActiveUserStore activeUserStore;

    private TokenProvider jwtTokenUtil = new TokenProvider();

    public LoggedUser(String username, ActiveUserStore activeUserStore) {
        this.username = username;
        this.activeUserStore = activeUserStore;
    }

    public LoggedUser() {}

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (!users.contains(user.getUsername())) {
            users.add(user.getUsername());
        }
    }


//    public void valueUnbound(HttpServletRequest req) {
//    public void valueUnbound(HttpSessionBindingEvent event) {
//        String token = jwtTokenUtil.resolveToken(req);
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        List<String> users = activeUserStore.getUsers();
//        LoggedUser user = (LoggedUser) event.getValue();
//
//        if(username != null){
//            System.out.println(user.getUsername() + "  <= user name n Unbound");
//            if (users.contains(username)) {
//                users.remove(username);
//                activeUserStore.removeSpecificUser(username);
//            }
//        }
//        else {
//            System.out.println("User is null");
//        }
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ActiveUserStore getActiveUserStore() {
        return activeUserStore;
    }

    public void setActiveUserStore(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }
}
