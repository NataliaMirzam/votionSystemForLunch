package nataliamirzam.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//https://sabljakovich.medium.com/adding-basic-auth-authorization-option-to-openapi-swagger-documentation-java-spring-95abbede27e9
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "REST API documentation",
                version = "1.0",
                description = """
                        REST API using Hibernate/Spring/SpringMVC.
                                                           
                            A voting system for deciding where to have lunch.
                                                           
                            - 2 types of users: admin and regular users
                            - Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
                            - Menu changes each day (admins do the updates)
                            - Users can vote for a restaurant they want to have lunch at today
                            - Only one vote counted per user
                            - If user votes again the same day:
                                - If it is before 11:00 we assume that he changed his mind.
                                - If it is after 11:00 then it is too late, vote can't be changed
                            Each restaurant provides a new menu each day.
                        """,
                contact = @Contact(name = "Sidorova Natalia", email = "natalia.alex.st@gmail.com")
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("REST API")
                .pathsToMatch("/api/**")
                .build();
    }
}
