package br.com.ciaf.admin.config.master;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "br.com.ciaf.admin.master.repositories",
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef = "masterTransactionManager"
)
public class MasterEmitenteJpaConfig {
    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;
    private final JpaVendorAdapter jpaVendorAdapter;

    public MasterEmitenteJpaConfig(
            JpaProperties jpaProperties,
            HibernateProperties hibernateProperties,
            JpaVendorAdapter jpaVendorAdapter) {
        this.jpaProperties = jpaProperties;
        this.hibernateProperties = hibernateProperties;
        this.jpaVendorAdapter = jpaVendorAdapter;
    }

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "masterEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(
            @Qualifier("masterDataSource") DataSource masterDataSource) {
        Map<String, Object> properties = new HashMap<>(
                hibernateProperties.determineHibernateProperties(
                        jpaProperties.getProperties(), new HibernateSettings()
                )
        );

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(masterDataSource);
        em.setPackagesToScan("br.com.ciaf.admin.entities");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaPropertyMap(properties);
        em.setPersistenceUnitName("master-emitente");
        return em;
    }

    @Bean(name = "masterTransactionManager")
    public JpaTransactionManager masterTransactionManager(
            @Qualifier("masterEntityManagerFactory") EntityManagerFactory masterEntityManagerFactory) {
        return new JpaTransactionManager(masterEntityManagerFactory);
    }
}
