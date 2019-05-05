package ch.keepcalm.kotlinevents

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.RequestHandler
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not<RequestHandler>(RequestHandlerSelectors.basePackage("org.springframework")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
    }

//    @Bean
//    fun apiAccount(): Docket {
//        return Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("ch.keepcalm.kotlinevents"))
//                .paths(regex("/account.*"))
//                .build()
//                .apiInfo(metaData())
//    }

    private fun metaData(): ApiInfo {
        return ApiInfoBuilder()
                .title("Spring Boot REST API")
                .description("Spring Boot REST API for Kotlin Axon Service")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(Contact("Marcel Widmer", "https://docs.marcelwidmer.org", "info@keepcalm.ch"))
                .build()
    }
}
