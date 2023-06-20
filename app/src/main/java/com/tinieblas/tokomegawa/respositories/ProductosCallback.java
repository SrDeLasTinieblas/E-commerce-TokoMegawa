package com.tinieblas.tokomegawa.respositories;

import com.tinieblas.tokomegawa.domain.models.ProductosItem;

import java.util.List;

public interface ProductosCallback {
    void onProductosFetched(List<ProductosItem> productos);
}
