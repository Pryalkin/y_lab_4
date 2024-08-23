package com.pryalkin.dao;

import java.util.List;

public interface Dao<T, V> {

    T findById(V v);
    void save(T t);
    void update(T t);
    void delete(T t);
    List<T> findAll();

}
