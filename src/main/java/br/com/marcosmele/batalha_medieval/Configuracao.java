package br.com.marcosmele.batalha_medieval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.marcosmele.batalha_medieval.api.BatalhaApiInterceptor;
import br.com.marcosmele.batalha_medieval.servico.ServicoBatalha;

@Configuration
public class Configuracao implements WebMvcConfigurer
{
	
	@Autowired
	private ServicoBatalha servico;

    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new BatalhaApiInterceptor(servico))
        	.addPathPatterns("/api/**")
        	.excludePathPatterns("/api/iniciar","/api/herois");
        
    }
}