package com.beautyathome.security;

/**
 * Configuración de seguridad para la API REST.
 * 
 * NOTA: Actualmente deshabilitado. Para activar Spring Security:
 * 1. Descomentar la anotación @Configuration y @EnableWebSecurity
 * 2. Agregar la dependencia spring-boot-starter-security en pom.xml
 * 3. Implementar los filtros y autenticación necesarios
 * 
 * Ejemplo de configuración básica:
 * 
 * <pre>
 * {@code
 * @Configuration
 * @EnableWebSecurity
 * public class SecurityConfig {
 *     
 *     @Bean
 *     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 *         http
 *             .csrf(csrf -> csrf.disable())
 *             .authorizeHttpRequests(auth -> auth
 *                 .requestMatchers("/api/**").authenticated()
 *                 .anyRequest().permitAll()
 *             )
 *             .httpBasic(Customizer.withDefaults());
 *         return http.build();
 *     }
 * }
 * }
 * </pre>
 */
public class SecurityConfig {
    // Preparado para futura implementación de Spring Security
    // Ver documentación arriba para instrucciones de activación
}
