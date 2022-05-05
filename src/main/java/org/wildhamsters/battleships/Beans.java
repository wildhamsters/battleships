package org.wildhamsters.battleships;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dominik Żebracki
 */
@Configuration
class Beans {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService battleshipsUserDetailsService(Users users) {
        return new UsersService(users);
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemp(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public Users users(NamedParameterJdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        return new DatabaseUsers(jdbcTemplate, passwordEncoder);
    }
}
