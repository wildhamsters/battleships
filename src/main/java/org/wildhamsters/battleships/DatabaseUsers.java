package org.wildhamsters.battleships;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

/**
 * @author Dominik Żebracki
 */
class DatabaseUsers implements Users {

    private final static String FIND_USER_BY_USERNAME_QUERY = "SELECT * FROM users WHERE name = :username";
    private final static String INSERT_USER_QUERY = "INSERT INTO users(name, email, password, account_non_expired," +
            "account_non_locked, credentials_non_expired, enabled, authority) values (:username, :email, :password, " +
            ":account_non_expired, :account_non_locked, :credentials_non_expired, :enabled, :authority)";

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
                        "account_non_expired", false,
                        "account_non_locked", false,
                        "credentials_non_expired", false,
                        "enabled", false,
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
                            rs.getBoolean("account_non_expired"),
                            rs.getBoolean("account_non_locked"),
                            rs.getBoolean("credentials_non_expired"),
                            rs.getBoolean("enabled")));
        } catch (EmptyResultDataAccessException e) {
            userEntity = null;
        }
        return userEntity;
    }
}
