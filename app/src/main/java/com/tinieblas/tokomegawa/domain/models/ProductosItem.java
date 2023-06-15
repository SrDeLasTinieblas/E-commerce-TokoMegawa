package com.tinieblas.tokomegawa.domain.models;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductosItem implements Serializable {

	@SerializedName("idProducto")
	private int idProducto;
	@SerializedName("nombreProducto")
	private String nombreProducto;
	@SerializedName("descripcionProducto")
	private String descripcionProducto;
	@SerializedName("imagen1")
	private String imagen1;
	@SerializedName("imagen2")
	private String imagen2;
	@SerializedName("imagen3")
	private String imagen3;
	@SerializedName("precioUnitario")
	private double precioUnitario;
	@SerializedName("cantidadDisponible")
	private int cantidadDisponible;
	@SerializedName("categoria")
	private String categoria;
	@SerializedName("delivery")
	private String delivery;

	private int amount;

	public ProductosItem(int idProducto, String nombreProducto, String descripcionProducto, String imagen1, String imagen2, String imagen3, double precioUnitario, int cantidadDisponible, String categoria, String delivery, int amount) {
		this.idProducto = idProducto;
		this.nombreProducto = nombreProducto;
		this.descripcionProducto = descripcionProducto;
		this.imagen1 = imagen1;
		this.imagen2 = imagen2;
		this.imagen3 = imagen3;
		this.precioUnitario = precioUnitario;
		this.cantidadDisponible = cantidadDisponible;
		this.categoria = categoria;
		this.delivery = delivery;
		this.amount = amount;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public String getImagen1() {
		return imagen1;
	}

	public void setImagen1(String imagen1) {
		this.imagen1 = imagen1;
	}

	public String getImagen2() {
		return imagen2;
	}

	public void setImagen2(String imagen2) {
		this.imagen2 = imagen2;
	}

	public String getImagen3() {
		return imagen3;
	}

	public void setImagen3(String imagen3) {
		this.imagen3 = imagen3;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ProductosItem{" +
				"idProducto=" + idProducto +
				", nombreProducto='" + nombreProducto + '\'' +
				", descripcionProducto='" + descripcionProducto + '\'' +
				", imagen1='" + imagen1 + '\'' +
				", imagen2='" + imagen2 + '\'' +
				", imagen3='" + imagen3 + '\'' +
				", precioUnitario=" + precioUnitario +
				", cantidadDisponible=" + cantidadDisponible +
				", categoria='" + categoria + '\'' +
				", delivery='" + delivery + '\'' +
				'}';
	}
}










