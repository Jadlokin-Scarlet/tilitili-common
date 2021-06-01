package com.tilitili.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

//@Configuration
public class DataSourceConfig {

    @Value("${mapper-locations}")
    private String mapperLocations;
    @Value("${type-aliases-package}")
    private String typeAliasesPackage;
    @Value("${configuration.map-underscore-to-camel-case}")
    private Boolean mapUnderscoreToCamelCase;
    @Value("${configuration.use-generated-keys}")
    private Boolean useGeneratedKeys;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.tilitili")
    public DataSource tilitiliDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean
    @Primary
    public DataSourceTransactionManager tilitiliTransactionManager(DataSource tilitiliDataSource) {
        return new DataSourceTransactionManager(tilitiliDataSource);
    }
    @Bean
    @Primary
    public SqlSessionFactory tilitiliSqlSessionFactory(DataSource tilitiliDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(tilitiliDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        configuration.setUseGeneratedKeys(useGeneratedKeys);
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }



//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.mysql")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//    @Bean
//    public DataSourceTransactionManager mysqlTransactionManager(DataSource mysqlDataSource) {
//        return new DataSourceTransactionManager(mysqlDataSource);
//    }
//    @Bean
//    public SqlSessionFactory mysqlSqlSessionFactory(DataSource mysqlDataSource) throws Exception {
//        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(mysqlDataSource);
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
//        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
//        configuration.setUseGeneratedKeys(useGeneratedKeys);
//        sessionFactory.setConfiguration(configuration);
//        return sessionFactory.getObject();
//    }
}
