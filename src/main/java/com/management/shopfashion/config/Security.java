package com.management.shopfashion.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Security {
	@Value("${jwt.signerkey}")
	private String signerKey;
	@Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
    
        config.setAllowCredentials(true);  // Cho phép gửi cookie và thông tin xác thực
        config.addAllowedOrigin("http://localhost:3000"); // Địa chỉ ứng dụng React
        config.addAllowedHeader("Authorization"); // Cho phép tất cả header
       
        config.addAllowedHeader("*");
        
        config.addAllowedMethod("*"); // Cho phép tất cả phương thức HTTP (GET, POST, PUT, DELETE
//        CorsRegistry corsregister=new CorsRegistry();
//        addCros(corsregister);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Áp dụng cho tất cả các endpoint
        return new CorsFilter(source);
    }
	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
		 
		 httpSecurity.csrf(AbstractHttpConfigurer::disable);
		 httpSecurity.cors().disable()
		 .authorizeRequests()
		 .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll();

		 httpSecurity.authorizeRequests(request->
		 request
		 .requestMatchers(HttpMethod.GET,"/api/auth/exam").permitAll()
		 .requestMatchers(
	                "/swagger-ui/**",
	                "/v3/api-docs/**"
	            ).permitAll()
	
//		 .anyRequest().authenticated()
		 .anyRequest().permitAll()
	      
		 );
		 return httpSecurity.build();
	 }
	@Bean
	 JwtDecoder jwtDecoder() {
		 SecretKeySpec secretkeySpec=new SecretKeySpec(signerKey.getBytes(),"HS512");
		return NimbusJwtDecoder
				.withSecretKey(secretkeySpec)
				.macAlgorithm(MacAlgorithm.HS512)
				.build();
//		 return null;
		
		 
	 };
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder(10);
	}
	@Bean
	public Cloudinary cloudinary() {
		
		Cloudinary cloud=new Cloudinary(ObjectUtils.asMap(
				"cloud_name","dlirihyoh",
				"api_key","567743117647528",
				"api_secret","UqeOUeJH5YQ6GwiioZQthWfVS0o",
				"secure",true
				));
		return cloud;
	}
	 @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
}
