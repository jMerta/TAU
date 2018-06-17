package io.jmerta.tau.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
@PropertySources({
        @PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
})
public class AppConfig {

    @Bean
    public ServletRegistrationBean restServlet(ApplicationContext applicationContext) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/*");
        servletRegistrationBean.setName("rest-services");
        dispatcherServlet.setApplicationContext(applicationContext);

        return servletRegistrationBean;
    }

}
