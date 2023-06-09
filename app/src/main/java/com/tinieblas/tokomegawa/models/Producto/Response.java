package com.tinieblas.tokomegawa.models.Producto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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