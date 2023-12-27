package core.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import core.filter.FilterInterceptor;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@PropertySource("classpath:default-base.properties")
public class MvcConfiguration {

    public static final String HEADER_NAME_X_ANY_KEY_AUTH_ID = "X-ANY-KEY-AUTH-ID";

    @Bean
    public OpenAPI setGlobalAuthorizationHeaderIntoSwagger() {
      return new OpenAPI().components(
                    new Components()
                        .addSecuritySchemes(
                            HEADER_NAME_X_ANY_KEY_AUTH_ID, 
                            new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(HEADER_NAME_X_ANY_KEY_AUTH_ID)
                        )
                )
                .addSecurityItem(
                    new SecurityRequirement().addList(HEADER_NAME_X_ANY_KEY_AUTH_ID)
                );
    }
    

    @Bean
    public WebMvcConfigurer webMvcConfigurer(@Autowired FilterInterceptor filterIntercept)
    {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods("GET", "POST","PUT","DELETE","OPTIONS","PATCH")
                        .allowedHeaders("Access-Control-Allow-Headers","X-Requested-With","Authorization","Content-Type","X-File-Name", HEADER_NAME_X_ANY_KEY_AUTH_ID)
                        .exposedHeaders("Access-Control-Expose-Headers", "X-File-Name", "Content-Disposition")
                        .allowCredentials(true)
                        .maxAge(3600); 
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry
                    .addInterceptor(filterIntercept)
                    .excludePathPatterns("/swagger*/**");
            }
        };
    }
    
    
}
