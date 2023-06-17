package com.tinieblas.tokomegawa.models.Categorias;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public abstract class CategoriasResponse{

	@SerializedName("categorias")
	private List<String> categorias;

	public void setCategorias(List<String> categorias){
		this.categorias = categorias;
	}

	public List<String> getCategorias(){
		return categorias;
	}

	@Override
 	public String toString(){
		return 
			"CategoriasResponse{" + 
			"categorias = '" + categorias + '\'' + 
			"}";
		}

	public abstract void onCategoriasFetched(List<String> categorias);
}