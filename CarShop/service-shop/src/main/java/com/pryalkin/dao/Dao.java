package com.pryalkin.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, V> {

    Optional<T> findById(V v);
    void save(T t);
    void update(T t);
    void delete(T t);
    Optional<List<T>> findAll();

}
