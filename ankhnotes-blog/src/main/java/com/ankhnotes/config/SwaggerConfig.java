package com.ankhnotes.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //开启swagger，即可通过swagger为huanf-blog工程生成接口文档
/**
 * Swagger用于自动生成接口文档, 启动类启动后通过localhost:7777/swagger-ui.html访问获取
 */
public class SwaggerConfig {

    @Bean
    // 创建API基本信息
    public Docket createTestApi() {
        return new Docket(DocumentationType.SWAGGER_2)

                .apiInfo(apiInfo())

                .select()
                // 对所有api进行监控
                .apis(RequestHandlerSelectors.any())
                //不显示自动生成的错误路径接口，也就是错误路径不监控
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("Ankhnotes-blog", "https://ankhnotes.cn/", "1214959101@qq.com");
        return new ApiInfoBuilder()
                .title("Ankhnotes-blog接口文档")
                .description("Ankhnotes-blog的前台接口信息")
                .contact(contact)
                .version("1.0.0")
                .build();
    }

}
