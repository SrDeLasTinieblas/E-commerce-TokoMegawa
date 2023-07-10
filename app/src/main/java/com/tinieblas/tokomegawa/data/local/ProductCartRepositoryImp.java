package com.tinieblas.tokomegawa.data.local;

import android.content.Context;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.data.local.base.SharedRepo;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.domain.repository.ProductCartRepository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ProductCartRepositoryImp extends SharedRepo implements ProductCartRepository {
    private static final String NAME = "productos_carrito";
    private static final String KEY_LIST = "lista_productos_carrito";

    public ProductCartRepositoryImp(Context context) {
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

        return gson.fromJson(listaProductosJson, type);
    }

    @Override
    public void insertAll(List<ProductosItem> list) {
        String carritoJson = new Gson().toJson(list);
        putString(KEY_LIST, carritoJson);
    }

    @Override
    public void deleteAll() {
        clear();
    }

    @Override
    public void delete(Integer id) {
        List<ProductosItem> list = getAll();

        Integer deleteId = -1;

        for (int index = 0; index < list.size(); index++) {
            ProductosItem item = list.get(index);

            if (item.getIdProducto() == id) {
                deleteId = index;

                break;
            }

        }

        if (deleteId != -1) {
            list.remove(deleteId);
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
