package com.tinieblas.tokomegawa.data.local;

import android.content.Context;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.data.local.base.SharedRepo;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.domain.repository.ProductsViewedRepository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsViewedRepositoryImp extends SharedRepo implements ProductsViewedRepository {

    private static final String NAME = "productos_vistos";

    private final String KEY_LIST = "lista_productos_vistos";
    public ProductsViewedRepositoryImp(Context context) {
        super(context, NAME);
    }

    @Override
    public List<ProductosItem> getAll() {
        String listaProductosJson = getString(KEY_LIST);
        if (listaProductosJson.isEmpty()) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductosItem>>() {
        }.getType();

        List<ProductosItem> listaProductosItems = gson.fromJson(listaProductosJson, type);
        Map<Integer, ProductosItem> productosMap = new HashMap<>();
        for (ProductosItem item : listaProductosItems) {
            productosMap.put(item.getIdProducto(), item);
        }

        return new ArrayList<>(productosMap.values());
    }

    @Override
    public void insertAll(List<ProductosItem> list) {
        Gson gson = new Gson();
        String carritoJson = gson.toJson(list);
        putString(KEY_LIST, carritoJson);
    }

    @Override
    public void deleteAll() {
        clear();
    }

    @Override
    public void delete(Integer id) {
        List<ProductosItem> list = new ArrayList<>(getAll());

        Integer deleteId = -1;

        for (int index = 0; index < list.size(); index++) {
            ProductosItem item = list.get(index);

            if (item.getIdProducto() == id) {
                deleteId = index;
                break;
            }

        }
        int delete = Integer.parseInt(String.valueOf(deleteId));

        if (deleteId != -1) {
            list.remove(delete);
        }
        insertAll(list);

    }

    @Override
    public ProductosItem get(Integer id) {
        List<ProductosItem> list = getAll();
        if (!list.isEmpty()) {
            for (int index = 0; index < list.size(); index++) {
                ProductosItem item = list.get(index);
                if (item.getIdProducto() == id) {
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public void save(ProductosItem product) {
        delete(product.getIdProducto());
        List<ProductosItem> list = getAll();
        list.add(product);
        insertAll(list);
    }
}
