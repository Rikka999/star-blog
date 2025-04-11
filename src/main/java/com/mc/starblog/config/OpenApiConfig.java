package com.mc.starblog.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("StarBlog API")      // API 标题
                        .version("1.0.0")          // 版本号
                        .description("star-blog接口文档") // 详细描述
                        .contact(new Contact()     // 联系方式（可选）
                                .name("开发者团队")
                                .email("evileyerikka@fox.com"))
                        .license(new License()      // 许可证（可选）
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
