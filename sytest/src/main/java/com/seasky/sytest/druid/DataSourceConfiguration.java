package com.seasky.sytest.druid;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.write")
    public DataSource writeDataSource() {
        //配置写库信息到数据源对象
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.read")
    public DataSource readDataSource() {
        //配置读库信息到数据源对象
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public AbstractRoutingDataSource dataSource() {
        //配置动态数据源
        WriteReadRoutingDataSource proxy = new WriteReadRoutingDataSource();
        Map<Object, Object> targetDataResources = new HashMap<>();
        targetDataResources.put(DataSourceName.write, writeDataSource());
        targetDataResources.put(DataSourceName.read, readDataSource());
        proxy.setDefaultTargetDataSource(writeDataSource());
        proxy.setTargetDataSources(targetDataResources);
        // proxy.afterPropertiesSet();
        return proxy;
    }

    // @Bean
    // @ConfigurationProperties(prefix = "mybatis-plus")
    // public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception {
    //     MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
    //     mybatisPlus.setDataSource(dataSource());
    //     mybatisPlus.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
    //     mybatisPlus.setPlugins(new Interceptor[]{new PaginationInterceptor()});
    //     return mybatisPlus;
    // }
}

