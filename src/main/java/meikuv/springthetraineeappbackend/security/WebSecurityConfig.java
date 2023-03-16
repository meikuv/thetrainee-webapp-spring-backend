package meikuv.springthetraineeappbackend.security;

import meikuv.springthetraineeappbackend.security.jwt.AuthEntryPointJwt;
import meikuv.springthetraineeappbackend.security.jwt.AuthTokenFilter;
import meikuv.springthetraineeappbackend.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("X-PINGOTHER", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/signin/**").permitAll()
                .antMatchers("/api/v1/auth/signup/**").permitAll()
                .antMatchers("/api/v1/auth/logout/**").permitAll()
                .antMatchers("/api/v1/signup/confirm/**").permitAll()
                .antMatchers("/api/v1/userInfo/**").permitAll()
                .antMatchers("/api/v1/vacancy/createVacancy/**").permitAll()
                .antMatchers("/api/v1/vacancy/allVacancy/**").permitAll()
                .antMatchers("/api/v1/vacancy/**").permitAll()
                .antMatchers("/api/v1/vacancy/companyVacancy/**").permitAll()
                .antMatchers("/api/v1/vacancy/delete/**").permitAll()
                .antMatchers("/api/v1/vacancy/update/**").permitAll()
                .antMatchers("/api/v1/vacancy/respond/**").permitAll()
                .antMatchers("/api/v1/vacancy/respondResult/**").permitAll()
                .antMatchers("/api/v1/resume/createResume/**").permitAll()
                .antMatchers("/api/v1/resume/userResume/**").permitAll()
                .antMatchers("/api/v1/resume/delete/**").permitAll()
                .anyRequest().authenticated().and().formLogin().permitAll().and().logout().permitAll();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
