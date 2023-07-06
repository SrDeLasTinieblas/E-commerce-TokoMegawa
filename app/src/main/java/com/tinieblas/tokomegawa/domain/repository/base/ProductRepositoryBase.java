package com.tinieblas.tokomegawa.domain.repository.base;

import java.util.List;

public interface ProductRepositoryBase<T> {

    List<T> getAll();
    void insertAll(List<T> list);
    void deleteAll();
    void delete(Integer id);
    T get(Integer id);
    void save(T product);

}


