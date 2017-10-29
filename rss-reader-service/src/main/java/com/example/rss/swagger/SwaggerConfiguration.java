package com.example.rss.swagger;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import com.example.rss.controller.DnesBgController;
import com.google.common.base.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = { DnesBgController.class })
public class SwaggerConfiguration extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfiguration.class);

    /**
     * Docket for Rss API.
     * 
     * @return Docket
     */
    @Bean
    public Docket rssApig() {
        logger.info("Configuring Swagger for ap-rsspay");
        return new Docket(DocumentationType.SWAGGER_2).groupName("rss-api").apiInfo(getApiInfo()).select()
                .paths(getApiPaths()).build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("Rss news API").description("Rss Reader").contact("dev.example@example.com")
                .version("v1").build();
    }

    @SuppressWarnings("unchecked")
    private Predicate<String> getApiPaths() {
        return or(regex("/dnesbg/.*"), regex("/v1/.*"));
    }

    /**
     * Adding the property spring.resources.add-mappings: false to the
     * application.yml was required for OP-404; in order to stop spring
     * exceptions being returned instead of json error blocks. But adding that
     * flag stopped access to resources; so now they have to be manually added.
     * And this includes access to the swagger html.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
