package br.com.ciaf.admin.config.multitenant;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class TenantInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        String uri = req.getRequestURI();
        // Force master schema for authentication endpoints
        if (uri != null && uri.startsWith("/auth")) {
            Optional.ofNullable(req.getHeader("schema"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(String::toLowerCase)
                    .ifPresentOrElse(
                            TenantContext::setCurrentTenant,
                            () -> TenantContext.setCurrentTenant(TenantContext.DEFAULT_TENANT)
                    );

            return true;
        } else if (req.getHeader("schema") != null && !req.getHeader("schema").equals("ciaf_id")) {
            Optional.ofNullable(req.getHeader("schema"))
                    .map(String::toLowerCase)
                    .ifPresent(TenantContext::setCurrentTenant);
            return true;
        } else {
            TenantContext.setCurrentTenant(TenantContext.DEFAULT_TENANT);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }

}
