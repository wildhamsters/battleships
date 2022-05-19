package org.wildhamsters.battleships;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Piotr Chowaniec
 */
@Service
class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void registerUser(UserDto userDto) throws AccountExistException {
        if (userRepository.findByName(userDto.name()).isPresent()) {
            Logger.log(Log.Level.ERROR, this.getClass(),
                    "Trying to create new account for existing user: %s".formatted(userDto.name()));
            throw new AccountExistException("An account for username %s already exist.".formatted(userDto.name()));
        }
        userRepository.save(new UserEntityMapper().map(userDto));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByName(username);

        if (!userEntity.isPresent()) {
            Logger.log(Log.Level.ERROR, this.getClass(), "User not found: %s".formatted(username));
            throw new UsernameNotFoundException("Not found: " + username);
        }

        return User.withUsername(userEntity.get().getName())
                .password(userEntity.get().getPassword())
                .authorities(userEntity.get().getAuthority())
                .accountExpired(userEntity.get().isAccountExpired())
                .accountLocked(userEntity.get().isAccountLocked())
                .credentialsExpired(userEntity.get().isCredentialsExpired())
                .disabled(userEntity.get().isDisabled())
                .build();
    }
}
