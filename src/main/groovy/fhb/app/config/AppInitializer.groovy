package fhb.app.config

import javax.servlet.Registration;
import javax.servlet.ServletContext
import javax.servlet.ServletContextListener
import javax.servlet.ServletException
import javax.servlet.ServletRegistration;

import org.springframework.core.io.support.ResourcePropertySource
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.servlet.DispatcherServlet

class AppInitializer implements WebApplicationInitializer {

    @Override
    void onStartup(ServletContext servletContext) throws ServletException {
        configPropertySource(servletContext)
        AnnotationConfigWebApplicationContext rootContext = createSpringApplicationContext()
        rootContext.environment.propertySources.addFirst(propertySource(servletContext))
        addListener(servletContext, rootContext)
        addFilter(servletContext, rootContext)
        addSpringWebMVCServlet(servletContext, rootContext)
    }

    static addListener(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext) {
        servletContext.addListener(new ContextLoaderListener(rootContext))
        servletContext.addListener(new RequestContextListener())
        servletContext.addListener(createAuthServiceServletLifecycleListener(servletContext) as ServletContextListener)
    }

    static addFilter(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext) {
        servletContext.setAttribute("environment", rootContext.environment)
        Registration.Dynamic encodingFilter = servletContext.addFilter('encoding', CharacterEncodingFilter)
        encodingFilter.addMappingForUrlPatterns(null, true, '/*')
        encodingFilter.setInitParameter('encoding', 'UTF-8')
        encodingFilter.setInitParameter('forceEncoding', 'true')
    }

    static addSpringWebMVCServlet(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext) {
        DispatcherServlet webMvcServlet = new DispatcherServlet(rootContext)
        ServletRegistration.Dynamic registration = servletContext.addServlet('dispatcher', webMvcServlet)
        registration.addMapping("/*")
    }

    static createAuthServiceServletLifecycleListener(ServletContext servletContext) {
        return Class.forName(servletContext.getInitParameter('auth.service.servlet.lifecycle.listener')).newInstance()
    }

    static AnnotationConfigWebApplicationContext createSpringApplicationContext() {
        final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext()
        rootContext.register(SpringConfig)
        rootContext.setDisplayName('Registration API')
        return rootContext
    }

    static ResourcePropertySource propertySource(ServletContext servletContext) {
        return new ResourcePropertySource(servletContext.getInitParameter("registration_properties_location"))
    }

    static configPropertySource(ServletContext servletContext) {
        servletContext.setInitParameter('registration_properties_location', 'file:///e42/etc/registration-api/registration-api.properties')
    }
}

