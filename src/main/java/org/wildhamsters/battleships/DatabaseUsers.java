package org.wildhamsters.battleships;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Dominik Żebracki
 */
@Transactional
class DatabaseUsers implements Users {

    private final static String FIND_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE name = :username";
    private final static String INSERT_USER_QUERY = "INSERT INTO users(name, email, password, acc_expired," +
            "acc_locked, cred_expired, disabled, authority) values (:username, :email, :password, " +
            ":acc_expired, :acc_locked, :cred_expired, :disabled, :authority)";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    DatabaseUsers(NamedParameterJdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(UserDto userDto) throws AccountExistException {
        if(findByUsername(userDto.name()) != null) {
            throw new AccountExistException("An account for username %s already exist.".formatted(userDto.name()));
        }
        jdbcTemplate.update(INSERT_USER_QUERY,
                Map.of("username", userDto.name(),
                        "email", userDto.email(),
                        "password", passwordEncoder.encode(userDto.password()),
                        "acc_expired", false,
                        "acc_locked", false,
                        "cred_expired", false,
                        "disabled", false,
                        "authority", "USER"));
    }

    @Override
    public UserEntity findByUsername(String username) {
        UserEntity userEntity = null;
        try {
            userEntity = jdbcTemplate.queryForObject(FIND_USER_BY_USERNAME_QUERY,
                    Map.of("username", username),
                    (rs, rowNum) -> new UserEntity(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            new SimpleGrantedAuthority(rs.getString("authority")),
                            rs.getBoolean("acc_expired"),
                            rs.getBoolean("acc_locked"),
                            rs.getBoolean("cred_expired"),
                            rs.getBoolean("disabled")));
        } catch (EmptyResultDataAccessException e) {
            userEntity = null;
        }
        return userEntity;
    }
}
