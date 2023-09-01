package com.krakozhia.visa.configuration;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "DataSourceConfiguration",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.krakozhia.visa"}
)
public class DataSourceConfiguration {

    @Value("${db.url}")
    String dbUrl;

    @Value("${db.username}")
    String username;

    @Value("${db.passport}")
    String passport;

    public Map<String, String> jpaProperties() {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        jpaProperties.put("javax.persistence.transactionType", "JTA");
        return jpaProperties;
    }

    @Bean(name = "entityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), jpaProperties(), null
        );
    }


    @Bean(name = "DataSourceConfiguration")
    public LocalContainerEntityManagerFactoryBean getPostgresEntityManager(
            @Qualifier("entityManagerFactoryBuilder") EntityManagerFactoryBuilder entityManagerFactoryBuilder,
            @Qualifier("dataSource") DataSource postgresDataSource
    ) {
        return entityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("com.krakozhia.visa")
                .persistenceUnit("mysql")
                .properties(jpaProperties())
                .jta(true)
                .build();
    }

    @Bean("dataSourceProperties")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("dataSource")
    public DataSource dataSource(@Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties) {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dbUrl);
        mysqlXaDataSource.setUser(username);
        mysqlXaDataSource.setPassword(passport);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("xa_visa");
        xaDataSource.setMaxPoolSize(30);
        return xaDataSource;
    }

}