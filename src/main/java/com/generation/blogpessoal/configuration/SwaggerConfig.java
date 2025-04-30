package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI springBlogPessoalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto Blog Pessoal")
                        .description("Projeto Blog Pessoal - wdwf")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Loop")
                                .url("https://wdwf.github.io/portfolio/"))
                        .contact(new Contact()
                                .name("Weslley Ferreira")
                                .url("https://github.com/wdwf")
                                .email("w_dwf@hotmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github")
                        .url("https://github.com/wdwf"));
    }

    @Bean
    OpenApiCustomizer customerGlobalHeadOpenApiCustomizer() {

        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses apiResponses = operation.getResponses();
                apiResponses.addApiResponse("200", new ApiResponse().description("Sucesso!"));
                apiResponses.addApiResponse("201", new ApiResponse().description("Criado!"));
                apiResponses.addApiResponse("204", new ApiResponse().description("Sem Conteudo!"));
                apiResponses.addApiResponse("400", new ApiResponse().description("Erro na Requisição!"));
                apiResponses.addApiResponse("401", new ApiResponse().description("Não Autorizado!"));
                apiResponses.addApiResponse("403", new ApiResponse().description("Proibido!"));
                apiResponses.addApiResponse("404", new ApiResponse().description("Não Encontrado!"));
                apiResponses.addApiResponse("500", new ApiResponse().description("Erro na Aplicação!"));
            }));
        };
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}