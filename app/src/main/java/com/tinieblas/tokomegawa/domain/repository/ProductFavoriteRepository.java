package com.tinieblas.tokomegawa.domain.repository;

import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.domain.repository.base.ProductRepositoryBase;

import java.util.List;

public interface ProductFavoriteRepository extends ProductRepositoryBase<ProductosItem> {
    @Override
    List<ProductosItem> getAll();

    @Override
    void insertAll(List<ProductosItem> list);

    @Override
    void deleteAll();

    @Override
    void delete(Integer id);

    @Override
    ProductosItem get(Integer id);

    @Override
    void save(ProductosItem product);
}
