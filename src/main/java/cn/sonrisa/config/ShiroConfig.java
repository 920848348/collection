package cn.sonrisa.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Autowired UserRealm userRealm, @Autowired SessionDAO sessionDAO){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(userRealm);
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(sessionDAO);
        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> configMap = new LinkedHashMap<>();
        configMap.put("/","anon");
        configMap.put("/login","anon");
        configMap.put("/register","anon");
        configMap.put("/forget","anon");
        configMap.put("/manager/getCode","anon");
        configMap.put("/safety/forgetPwd","anon");
        configMap.put("/manager/**","authc");
        configMap.put("/safety/**","authc");
        configMap.put("/user/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(configMap);
        shiroFilterFactoryBean.setLoginUrl("/login");

        return shiroFilterFactoryBean;
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    @Bean(name = "sessionDAO")
    public MemorySessionDAO getSessionDAO(){
        return new MemorySessionDAO();
    }

}
