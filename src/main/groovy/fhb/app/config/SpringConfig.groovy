package fhb.app.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import


@Configuration
@ComponentScan(basePackages = 'fhb.app', excludeFilters = [@ComponentScan.Filter(Configuration)])
@Import([SpringMvcConfig])
class SpringConfig {
	
}

