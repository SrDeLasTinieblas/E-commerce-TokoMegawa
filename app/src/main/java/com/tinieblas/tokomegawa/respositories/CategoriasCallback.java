package com.tinieblas.tokomegawa.respositories;

import com.tinieblas.tokomegawa.domain.models.CategoriasResponse;

import java.util.List;

public interface CategoriasCallback {
    void onCategoriasFetched(List<CategoriasResponse> categorias);
}
