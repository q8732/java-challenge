package jp.co.axa.apidemo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration of springfox to focus on relevant apis.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(springfox.documentation.spring.web.plugins.Docket.class)
public class DocumentConfig {
    @Bean
    public Docket employeesAPI() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("jp.co.axa.apidemo"))
                .build()
                ;
    }
}
