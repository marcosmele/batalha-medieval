package br.com.marcosmele.batalha_medieval;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.marcosmele.batalha_medieval.api.BatalhaApiInterceptor;

@Configuration
public class Configuracao implements WebMvcConfigurer
{

    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new BatalhaApiInterceptor())
        	.addPathPatterns("/api/**")
        	.excludePathPatterns("/api/iniciar","/api/herois");
        
    }
}