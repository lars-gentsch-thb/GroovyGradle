package fhb.app.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.spring4.view.ThymeleafViewResolver
import org.thymeleaf.templateresolver.ServletContextTemplateResolver
import org.thymeleaf.templateresolver.TemplateResolver

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = 'fhb.app')
class SpringMvcConfig extends WebMvcConfigurerAdapter {
    
    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/")
        registry.addResourceHandler("/img/**").addResourceLocations("/img/")
        registry.addResourceHandler("/js/**").addResourceLocations("/js/")
    }

    @Bean
    TemplateResolver templateResolver() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver()

        templateResolver.setPrefix("/WEB-INF/views/")
        templateResolver.setSuffix(".html")
        templateResolver.setTemplateMode("XHTML")
        templateResolver.setCacheable(false)
        templateResolver.setCharacterEncoding("UTF-8")

        templateResolver
    }

    @Bean
    SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine()

        templateEngine.setTemplateResolver(templateResolver)
        templateEngine.afterPropertiesSet()

        templateEngine
    }

    @Bean
    ViewResolver viewResolver(TemplateEngine templateEngine) {
        ViewResolver viewResolver = new ThymeleafViewResolver()

        viewResolver.setTemplateEngine(templateEngine)
        viewResolver.setCharacterEncoding("UTF-8")
        viewResolver.setContentType("text/html; charset=UTF-8")
        viewResolver.setOrder(1)

        viewResolver
    }
}

