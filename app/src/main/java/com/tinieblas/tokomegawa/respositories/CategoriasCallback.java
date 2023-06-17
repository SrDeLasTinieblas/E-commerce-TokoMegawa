package com.tinieblas.tokomegawa.respositories;

import com.tinieblas.tokomegawa.models.Categorias.CategoriasResponse;

import java.util.List;

public interface CategoriasCallback {
    void onCategoriasFetched(List<CategoriasResponse> categorias);
}
