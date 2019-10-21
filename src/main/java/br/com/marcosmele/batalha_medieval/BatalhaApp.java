package br.com.marcosmele.batalha_medieval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BatalhaApp {
	
	public static void main(String[] args) {
		SpringApplication.run(BatalhaApp.class, args);	
	}
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("br.com.marcosmele.batalha_medieval.api"))              
          .paths(PathSelectors.any())    
          .build()
          .useDefaultResponseMessages(false)
          .apiInfo(info().build());                                           
    }
	
	private ApiInfoBuilder info() {
		 
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
 
		apiInfoBuilder.title("Batalha Medieval API");
		apiInfoBuilder.description("Api para realizar as jogadas da batalha medieval.");
		apiInfoBuilder.version("1.0");
		apiInfoBuilder.license("Licença - Open Source");
		apiInfoBuilder.contact(contato());
 
		return apiInfoBuilder;
 
	}
	private Contact contato() {
 
		return new Contact(
				"Marcos Mele",
				"https://linkedin.com/in/marcos-mele-23a85b42/", 
				"marcosmele1@gmail.com");
	}
	
}
