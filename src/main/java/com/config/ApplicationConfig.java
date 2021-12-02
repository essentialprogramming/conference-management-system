package com.config;


import com.api.resources.*;
import com.api.resources.filter.CorsFilter;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.Collections;

/**
 * JAX-RS application configuration class.
 */
@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(PaperController.class);
        register(AuthorController.class);
        register(ConferenceManagementController.class);
        register(ProgramCommitteeController.class);
        register(AuthenticationController.class);
        register(UploadController.class);

        register(MultiPartFeature.class);
        register(JacksonJaxbJsonProvider.class);
        register(CorsFilter.class);

        register(JacksonJaxbJsonProvider.class);

        OpenAPI openAPI = new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Conference Management API")
                        .description(
                                "Conference Management endpoints using OpenAPI 3.0")
                        .version("v1")
                )
                .schemaRequirement("Bearer", new SecurityScheme()
                        .name("Authorization")
                        .description("JWT Authorization header using the Bearer scheme. Example: \\\\\\\"Authorization: Bearer {token}\\\\\\\"")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                )
                .security(Collections.singletonList(new SecurityRequirement().addList("Bearer")));

        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(openAPI)
                .prettyPrint(true);

        AcceptHeaderOpenApiResource openApiResource = new AcceptHeaderOpenApiResource();
        openApiResource.setOpenApiConfiguration(oasConfig);
        register(openApiResource);
    }

}
