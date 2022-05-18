package org.wildhamsters.battleships;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Piotr Chowaniec
 */
@Repository
interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity save(UserEntity user);

    Optional<UserEntity> findByName(String name);
}
