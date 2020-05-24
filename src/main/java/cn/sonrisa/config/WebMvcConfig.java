package cn.sonrisa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/404").setViewName("error/404");
        registry.addViewController("/500").setViewName("error/500");
        registry.addViewController("/user/changePwd").setViewName("user/changePwd");
        registry.addViewController("/user/changeNickname").setViewName("user/changeNickname");
        registry.addViewController("/user/uploadHeadPortrait").setViewName("user/uploadHeadPortrait");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:F://pictures/");
//        registry.addResourceHandler("/images/**").addResourceLocations("file:/project/collection/images/");
    }

    //i18n装配
    @Bean
    public LocaleResolver localeResolver(){
        return new I18nResolver();
    }

}
