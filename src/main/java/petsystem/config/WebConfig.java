package petsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        String originsString = System.getenv("ALLOWED_ORIGINS");
        String[] allowedOrigins;

        if (originsString == null || originsString.isBlank()) {
            allowedOrigins = new String[]{"http://localhost:5173"};
        } else {
            allowedOrigins = Arrays.stream(originsString.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);
        }

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
