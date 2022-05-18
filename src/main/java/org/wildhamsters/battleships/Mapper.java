package org.wildhamsters.battleships;

/**
 * @author Piotr Chowaniec
 */
interface Mapper<K, V> {

    V map(K key);
}
