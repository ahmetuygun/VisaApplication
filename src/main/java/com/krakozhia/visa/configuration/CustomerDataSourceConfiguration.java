package com.krakozhia.visa.configuration;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "CustomerDataSourceConfiguration",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.krakozhia.visa"}
)
public class CustomerDataSourceConfiguration {

    public Map<String, String> customerJpaProperties() {
        Map<String, String> customerJpaProperties = new HashMap<>();
        customerJpaProperties.put("hibernate.hbm2ddl.auto", "none");
        customerJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        customerJpaProperties.put("hibernate.show_sql", "true");
        customerJpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        customerJpaProperties.put("javax.persistence.transactionType", "JTA");
        return customerJpaProperties;
    }

    @Bean(name = "customerEntityManagerFactoryBuilder")
    @Primary
    public EntityManagerFactoryBuilder customerEntityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(), customerJpaProperties(), null
        );
    }


    @Bean(name = "CustomerDataSourceConfiguration")
    @Primary
    public LocalContainerEntityManagerFactoryBean getPostgresEntityManager(
            @Qualifier("customerEntityManagerFactoryBuilder") EntityManagerFactoryBuilder customerEntityManagerFactoryBuilder,
            @Qualifier("customerDataSource") DataSource postgresDataSource
    ) {
        return customerEntityManagerFactoryBuilder
                .dataSource(postgresDataSource)
                .packages("com.krakozhia.visa")
                .persistenceUnit("mysql")
                .properties(customerJpaProperties())
                .jta(true)
                .build();
    }

    @Bean("customerDataSourceProperties")
    @Primary
    @ConfigurationProperties("datasource.customer")
    public DataSourceProperties customerDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean("customerDataSource")
    @Primary
    public DataSource customerDataSource(@Qualifier("customerDataSourceProperties") DataSourceProperties customerDataSourceProperties) {
        // return customerDataSourceProperties.initializeDataSourceBuilder().build();
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl("jdbc:mysql://localhost:3306/visa");
        mysqlXaDataSource.setUser("visa");
        mysqlXaDataSource.setPassword("visa");

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("xa_customer");
        xaDataSource.setMaxPoolSize(30);
        return xaDataSource;
    }



}