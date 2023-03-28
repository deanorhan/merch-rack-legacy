package org.daemio.merch.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Merch Service",
                version = "1.0.0",
                description = "Where you get all the merch",
                license = @License(name = "Apache 2.0", url = "http://foo.bar")
        )
)
public class OpenAPIConfig {
    
}
