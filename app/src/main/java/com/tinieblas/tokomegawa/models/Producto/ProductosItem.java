package com.tinieblas.tokomegawa.models.Producto;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductosItem implements Serializable {

	@SerializedName("delivery")
	private String delivery;

	@SerializedName("precioUnitario")
	private double precioUnitario;

	@SerializedName("imagen3")
	private String imagen3;

	@SerializedName("descripcionProducto")
	private String descripcionProducto;

	@SerializedName("categoria")
	private String categoria;

	@SerializedName("imagen2")
	private String imagen2;

	@SerializedName("idProducto")
	private int idProducto;

	@SerializedName("imagen1")
	private String imagen1;

	@SerializedName("nombreProducto")
	private String nombreProducto;

	@SerializedName("cantidadDisponible")
	private int cantidadDisponible;

	public ProductosItem(String delivery, double precioUnitario, String imagen3, String descripcionProducto, String categoria, String imagen2, int idProducto, String imagen1, String nombreProducto, int cantidadDisponible) {
		this.delivery = delivery;
		this.precioUnitario = precioUnitario;
		this.imagen3 = imagen3;
		this.descripcionProducto = descripcionProducto;
		this.categoria = categoria;
		this.imagen2 = imagen2;
		this.idProducto = idProducto;
		this.imagen1 = imagen1;
		this.nombreProducto = nombreProducto;
		this.cantidadDisponible = cantidadDisponible;
	}

	public void setDelivery(String delivery){
		this.delivery = delivery;
	}

	public String getDelivery(){
		return delivery;
	}

	public void setPrecioUnitario(double precioUnitario){
		this.precioUnitario = precioUnitario;
	}

	public double getPrecioUnitario(){
		return precioUnitario;
	}

	public void setImagen3(String imagen3){
		this.imagen3 = imagen3;
	}

	public String getImagen3(){
		return imagen3;
	}

	public void setDescripcionProducto(String descripcionProducto){
		this.descripcionProducto = descripcionProducto;
	}

	public String getDescripcionProducto(){
		return descripcionProducto;
	}

	public void setCategoria(String categoria){
		this.categoria = categoria;
	}

	public String getCategoria(){
		return categoria;
	}

	public void setImagen2(String imagen2){
		this.imagen2 = imagen2;
	}

	public String getImagen2(){
		return imagen2;
	}

	public void setIdProducto(int idProducto){
		this.idProducto = idProducto;
	}

	public int getIdProducto(){
		return idProducto;
	}

	public void setImagen1(String imagen1){
		this.imagen1 = imagen1;
	}

	public String getImagen1(){
		return imagen1;
	}

	public void setNombreProducto(String nombreProducto){
		this.nombreProducto = nombreProducto;
	}

	public String getNombreProducto(){
		return nombreProducto;
	}

	public void setCantidadDisponible(int cantidadDisponible){
		this.cantidadDisponible = cantidadDisponible;
	}

	public int getCantidadDisponible(){
		return cantidadDisponible;
	}

	@Override
	public String toString() {
		return "ProductosItem{" +
				"delivery='" + delivery + '\'' +
				", precioUnitario=" + precioUnitario +
				", imagen3='" + imagen3 + '\'' +
				", descripcionProducto='" + descripcionProducto + '\'' +
				", categoria='" + categoria + '\'' +
				", imagen2='" + imagen2 + '\'' +
				", idProducto=" + idProducto +
				", imagen1='" + imagen1 + '\'' +
				", nombreProducto='" + nombreProducto + '\'' +
				", cantidadDisponible=" + cantidadDisponible +
				'}';
	}
}