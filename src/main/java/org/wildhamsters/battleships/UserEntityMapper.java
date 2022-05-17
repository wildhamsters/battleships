package org.wildhamsters.battleships;

/**
 * @author Piotr Chowaniec
 */
class UserEntityMapper implements Mapper<UserDto, UserEntity> {

    @Override
    public UserEntity map(UserDto key) {
        return UserEntity.builder()
                .setName(key.name())
                .setPassword(key.password())
                .setEmail(key.email())
                .build();
    }
}
