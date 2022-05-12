package org.wildhamsters.battleships.play;

/**
 * Mapper that shall be implemented in mappers converting objects to entities or entities to objects.
 *
 * @author Piotr Chowaniec
 */
interface Mapper<T, S> {

    /**
     * Maps object to entity or entity to object depending on communication direction.
     *
     * @param mapSource object to be mapped.
     * @return mapping result.
     */
    S map(T mapSource);
}
