package org.wildhamsters.battleships;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
    @Profile("externalDatabase")
    public DataSource datasource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://@ec2-34-246-227-219.eu-west-1.compute.amazonaws.com:5432/d7gtr71j35ah6k?user=cegxpxbdutrnza&password=61b8b0aa523c4c16a5b69f0a11aed649845b31190a53cd499405255727338a67");
        dataSourceBuilder.username("cegxpxbdutrnza");
        dataSourceBuilder.password("61b8b0aa523c4c16a5b69f0a11aed649845b31190a53cd499405255727338a67");
        return dataSourceBuilder.build();
    }

    @Bean
    @Profile("externalDatabase")
    public NamedParameterJdbcTemplate jdbcTemp(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Profile("externalDatabase")
    public Users externalDatabaseUsers(NamedParameterJdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        return new DatabaseUsers(jdbcTemplate, passwordEncoder);
    }

    @Bean
    @Profile("inMemoryDatabase")
    public Users inMemoryUsers(PasswordEncoder passwordEncoder) {
        return new InMemoryUsers(new ConcurrentHashMap<>(), passwordEncoder);
    }
}
