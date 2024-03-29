package pl.portalstrzelecki.psback.component.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }
//todo ogarnac cala konfiguracje
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .cors()
//                .and().authorizeRequests()
//                .antMatchers("/authenticate").permitAll()
//                .antMatchers("/person_clubs").permitAll()
//                .antMatchers("/person_joinedevents").permitAll()
//                .antMatchers("/person").hasAuthority("USER")
//                .anyRequest().authenticated()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        http.csrf().disable()
//                .cors()
//                .and().authorizeRequests()
//                .antMatchers("/authenticate").permitAll()
//                .antMatchers("/person").hasAuthority("USER")
//                .antMatchers("/person/all").hasAuthority("USER")
//                .antMatchers("/person/all/basic").hasAuthority("USER")
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.csrf().disable()
                .cors()
                .and().authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/person/**").hasAuthority("USER")
                .antMatchers("/club/all").permitAll()
                .antMatchers("/club/**").hasAuthority("USER")
                .antMatchers("/event/all").permitAll()
                .antMatchers("/event/**").hasAuthority("USER")
                .antMatchers("/range/all").permitAll()
                .antMatchers("/range/**").hasAuthority("USER")
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //wymagane, żeby móc wstrzyknąć AuthenticationManagera do kontrolera
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




}


