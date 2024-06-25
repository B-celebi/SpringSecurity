package com.celebi.in_memory_auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration
{
    @Bean
    public BCryptPasswordEncoder defaultPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users()
    {

        UserDetails admin = User.builder()
                                .username("batuhan")
                                .password(defaultPasswordEncoder().encode("admin"))
                                .roles("ADMIN")
                                .build();

        UserDetails user = User.builder()
                               .username("batuhan2")
                               .password(defaultPasswordEncoder().encode("user"))
                               .roles("USER")
                               .build();

        UserDetails moderator = User.builder()
                                    .username("batuhan3")
                                    .password(defaultPasswordEncoder().encode("moderator"))
                                    .roles("MODERATOR")
                                    .build();


        return new InMemoryUserDetailsManager(user, admin, moderator);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception
    {
        security.csrf(CsrfConfigurer::disable)
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(FormLoginConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers("/public/**")
                                                 .permitAll())
                //.authorizeHttpRequests(x -> x.requestMatchers("/test/user/**").hasRole("USER"))
                //.authorizeHttpRequests(x -> x.requestMatchers("/test/admin/**").hasRole("ADMIN"))
                //.authorizeHttpRequests(x -> x.requestMatchers("/test/mod/**").hasRole("MODERATOR"))
                .authorizeHttpRequests(req -> req.anyRequest()
                                                 .authenticated())
                .httpBasic(Customizer.withDefaults());

        return security.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy()
    {
        String hierarchy = "ROLE_ADMIN > ROLE_MODERATOR \n ROLE_MODERATOR = ROLE_USER";
        // Hierarchy 2 : Only Admin > Moderator, Moderator !> User String hierarchy = "ROLE_ADMIN > ROLE_MODERATOR";
        return RoleHierarchyImpl.fromHierarchy(hierarchy);
        //return RoleHierarchyImpl.withDefaultRolePrefix().build();
    }
}
