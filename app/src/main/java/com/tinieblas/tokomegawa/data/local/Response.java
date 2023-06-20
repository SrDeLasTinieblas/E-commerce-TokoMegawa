package com.tinieblas.tokomegawa.data.local;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;

public class Response{

	@SerializedName("productos")
	private List<ProductosItem> productos;

	public void setProductos(List<ProductosItem> productos){
		this.productos = productos;
	}

	public List<ProductosItem> getProductos(){
		return productos;
	}
}