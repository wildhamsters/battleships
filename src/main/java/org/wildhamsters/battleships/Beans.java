package org.wildhamsters.battleships;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.wildhamsters.battleships.configuration.GameConfigurer;
import org.wildhamsters.battleships.play.GameRooms;

/**
 * @author Dominik Żebracki
 */
@Configuration
class Beans {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
