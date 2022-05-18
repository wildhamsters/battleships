package org.wildhamsters.battleships;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author Piotr Chowaniec
 */
class UserEntityMapper implements Mapper<UserDto, UserEntity> {

    @Override
    public UserEntity map(UserDto key) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(key.name());
        userEntity.setPassword(key.password());
        userEntity.setEmail(key.email());
        userEntity.setAuthority(new SimpleGrantedAuthority("USER"));
        userEntity.setAccountExpired(false);
        userEntity.setAccountLocked(false);
        userEntity.setCredentialsExpired(false);
        userEntity.setDisabled(false);
        return userEntity;
    }
}
