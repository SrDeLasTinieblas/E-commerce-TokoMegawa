package com.tinieblas.tokomegawa.domain.repository;

import com.tinieblas.tokomegawa.domain.models.ProductosItem;

import java.util.List;

public interface ApiRepository {
    List<ProductosItem> fetchProductos();
}
