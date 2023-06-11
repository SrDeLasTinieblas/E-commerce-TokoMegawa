package com.tinieblas.tokomegawa.respositories;

import com.tinieblas.tokomegawa.models.Producto.ProductosItem;

import java.util.List;

public interface ProductosCallback {
    void onProductosFetched(List<ProductosItem> productos);
}
