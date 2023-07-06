package com.tinieblas.tokomegawa.data.local;

import android.content.Context;

import com.tinieblas.tokomegawa.data.local.base.SharedRepo;
import com.tinieblas.tokomegawa.domain.repository.ProductsSavedRepository;

import java.util.Set;


// Productos Guardados
public class ProductSavedRepositoryImp extends SharedRepo implements ProductsSavedRepository {

    private static final String NAME = "productos_guardados_name";
    private static final String KEY_PRODUCTOS_GUARDADOS = "productos_guardados";

    public ProductSavedRepositoryImp(Context context) {
        super(context, NAME);
    }

    @Override
    public Set<String> getProductosGuardados() {
        return getStringSet(KEY_PRODUCTOS_GUARDADOS);
    }

    @Override
    public void saveProductosGuardados(Set<String> values) {
        putStringSet(KEY_PRODUCTOS_GUARDADOS, values);
    }


}
