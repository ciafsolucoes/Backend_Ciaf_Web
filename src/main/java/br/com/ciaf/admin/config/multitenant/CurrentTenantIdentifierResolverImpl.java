package br.com.ciaf.admin.config.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
//    @Override
//    public String resolveCurrentTenantIdentifier() {
//        return TenantContext.getCurrentTenant();
//    }
@Override
public String resolveCurrentTenantIdentifier() {
    String tenant = TenantContext.getCurrentTenant();
    if (tenant == null || tenant.isBlank()) {
        return TenantContext.DEFAULT_TENANT;
    }
    return tenant;
}


    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
