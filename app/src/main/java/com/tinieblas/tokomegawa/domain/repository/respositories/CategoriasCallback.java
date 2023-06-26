package com.tinieblas.tokomegawa.domain.repository.respositories;

import com.tinieblas.tokomegawa.domain.models.CategoriasResponse;

import java.util.List;

public interface CategoriasCallback {
    void onCategoriasFetched(List<CategoriasResponse> categorias);
}
