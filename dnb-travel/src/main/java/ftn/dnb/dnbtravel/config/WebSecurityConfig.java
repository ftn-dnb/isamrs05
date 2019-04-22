package ftn.dnb.dnbtravel.config;

import ftn.dnb.dnbtravel.security.TokenUtils;
import ftn.dnb.dnbtravel.security.auth.RestAuthenticationEntryPoint;
import ftn.dnb.dnbtravel.security.auth.TokenAuthenticationFilter;
import ftn.dnb.dnbtravel.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    TokenUtils tokenUtils;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // komunikacija izmedju klijenta i servera je stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // za neautorizovane zahteve posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                // svim korisnicima dopusti da pristupe putanjama /auth/** i /h2-console/**
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()

                // svaki zahtev mora biti autorizovan
                .anyRequest().authenticated().and()

                // presretni svaki zahtev filterom
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);

        http.csrf().disable();
        http.cors();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
        web.ignoring().antMatchers(HttpMethod.GET, "/api/airlines/**"); // obrisati kasnije
        web.ignoring().antMatchers(HttpMethod.GET, "/api/destinations");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/airplanes/**"); // da li svi imaju pristup listi aviona ?

        web.ignoring().antMatchers(HttpMethod.GET, "/api/airlines");   // Everyone has access to airlines
        web.ignoring().antMatchers(HttpMethod.GET, "/api/airlines/*");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/flights");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/flights/*");

        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js");

    }

}