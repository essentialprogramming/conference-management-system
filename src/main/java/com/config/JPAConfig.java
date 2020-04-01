package com.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableTransactionManagement
@ComponentScan({"com.entities", "com.model"})
@EnableJpaRepositories({"com.repository"})
public class JPAConfig {

//    private static final String DB_USERNAME = "proposal";
//    private static final String DB_PASSWORD = "proposal";
//    private static final String DB_DEFAULT_URL = "jdbc:hsqldb:file:./proposal/proposalDB;";
//    private static final String DB_URL_SYSTEM_PROPERTY = "proposal_db_url";
//    private static final String DB_URL = System.getProperty(DB_URL_SYSTEM_PROPERTY) != null ? System.getProperty(DB_URL_SYSTEM_PROPERTY) : DB_DEFAULT_URL;


   @Bean(name = "hikariDataSource")
   public HikariDataSource hikariDataSource(){
       HikariConfig config = new HikariConfig();
       config.setMinimumIdle(3);
       config.setMaximumPoolSize(10);
       config.setConnectionTimeout(3000);
       config.setIdleTimeout(TimeUnit.SECONDS.toMillis(10));
       config.setValidationTimeout(TimeUnit.SECONDS.toMillis(2));
       config.setDriverClassName(org.postgresql.Driver.class.getName());

       config.setJdbcUrl("jdbc:postgresql://localhost:5432/proposal");
       config.setUsername("postgres");
       config.setPassword("postgres");

       return new HikariDataSource(config);
   }

//    @Bean(name = "dataSource")
//    public DataSource dataSource() {
//
//        return new EmbeddedDatabaseBuilder().setDataSourceFactory(new DataSourceFactory() {
//            private final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//
//            @Override
//            public ConnectionProperties getConnectionProperties() {
//                return new ConnectionProperties() {
//                    @Override
//                    public void setDriverClass(Class<? extends Driver> aClass) {
//                        dataSource.setDriverClass(org.hsqldb.jdbcDriver.class);
//                    }
//
//                    @Override
//                    public void setUrl(String s) {
//                        dataSource.setUrl(DB_URL);
//                    }
//
//                    @Override
//                    public void setUsername(String s) {
//                        dataSource.setUsername(DB_USERNAME);
//                    }
//
//                    @Override
//                    public void setPassword(String s) {
//                        dataSource.setPassword(DB_PASSWORD);
//                    }
//                };
//            }
//
//            @Override
//            public DataSource getDataSource() {
//                return dataSource;
//            }
//        })
//                .addScript("classpath:schema.sql")
//                .build();
//    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(hikariDataSource());
        factoryBean.setPackagesToScan("com");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}

