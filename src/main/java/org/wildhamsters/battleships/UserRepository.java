package org.wildhamsters.battleships;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Piotr Chowaniec
 */
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity save(UserEntity user);

    UserEntity findByName(String name);
}
