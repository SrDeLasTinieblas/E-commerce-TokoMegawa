package com.tinieblas.tokomegawa.domain.repository;

import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.domain.repository.base.ProductRepositoryBase;

import java.util.List;
import java.util.Set;

public interface ProductsSavedRepository {
    Set<String> getProductosGuardados();
   void saveProductosGuardados(Set<String> values);

}
