package com.github.elbean.ignite.cache.domain.common;

/**
 * Specification interface.
 *
 * @param <T> The entity type that the specification applies to.
 */
public interface Specification<T> {

    boolean isSatisfiedBy(T t);

    Specification<T> and(Specification<T> specification);
}
