package com.seasky.sytest.shiro;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * desc
 *
 * @author Tomato
 * @date Created on 2020/11/17
 */
@Configuration
public class ShrioConfiguration {
    @Bean
    public DefaultWebSecurityManager securityManager(AuthorizingRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired @Lazy DefaultWebSecurityManager securityManager) throws IOException {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        factory.setLoginUrl("/");
        factory.setSecurityManager(securityManager);
        //以下内容，实际场景中应从配置文件获得
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // filterChainDefinitionMap.put("/user/login", "anon");
        // filterChainDefinitionMap.put("/user/create", "authc,perms[createRight]");
        // filterChainDefinitionMap.put("/user/query", "authc,roles[queryRole]");
        filterChainDefinitionMap.put("/user/**", "anon");
        factory.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factory;
    }
}
