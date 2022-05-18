package org.wildhamsters.battleships;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Piotr Chowaniec
 */
@Service
class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    void registerUser(UserDto userDto) throws AccountExistException {
        if(userRepository.findByName(userDto.name()).isPresent()) {
            throw new AccountExistException("An account for username %s already exist.".formatted(userDto.name()));
        }
        userRepository.save(new UserEntityMapper().map(userDto));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByName(username);
        userEntity.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
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
