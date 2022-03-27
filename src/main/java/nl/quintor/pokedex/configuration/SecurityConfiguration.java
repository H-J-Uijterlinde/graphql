package nl.quintor.pokedex.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/**")
//            .permitAll();
//    }
////
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER")
////                .and().withUser("trainer").password(passwordEncoder().encode("password")).roles("TRAINER", "USER");
////        auth.userDetailsService(userDetailsService());
////    }
//
//
//    @Bean
//    //@Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                    .username("user")
//                    .password(passwordEncoder().encode("password"))
//                    .roles("TRAINER", "USER")
//                    .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
////    @Bean
////    public Algorithm jwtAlgorithm() {
////        return Algorithm.HMAC256("P0k3m0nRuBy");
////    }
////
////    @Bean
////    public JWTVerifier verifier(Algorithm algorithm) {
////        return JWT
////                .require(algorithm)
////                .withIssuer("pokedex")
////                .build();
////    }
////
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }
////
////    @Bean
////    public AuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        provider.setUserDetailsService(userService);
////        provider.setPasswordEncoder(passwordEncoder);
////        return provider;
////    }
//}
