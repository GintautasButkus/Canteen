package lt.GintautasButkus.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
	
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String DELETE = "DELETE";
	private static final String PUT = "PUT";
	
	public WebMvcConfigurer corsConfirgurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods(GET, POST, DELETE, PUT)
				.allowedHeaders("*")
				.allowedOriginPatterns("*")
				.allowCredentials(true)
				;
			}
			
		};
	}

}
