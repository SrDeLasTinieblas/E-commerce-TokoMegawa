package com.tinieblas.tokomegawa.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelohotSales implements Parcelable {

    /**
     * Aqui serializamos el response que nos llega del main activity
     */

    //public static final String Parcel = "my_modelo";
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;


    /** Add */
    @SerializedName("cantidad")
    @Expose
    private int cantidad = 1;

    @SerializedName("preciototal")
    @Expose
    private float preciototal;

    @SerializedName("imagen1")
    @Expose
    private String imagen1;

    @SerializedName("imagen2")
    @Expose
    private String imagen2;

    @SerializedName("imagen3")
    @Expose
    private String imagen3;
    @SerializedName("imagen4")
    @Expose
    private String imagen4;
    @SerializedName("descuento")
    @Expose
    private float descuento;

    @SerializedName("categoria")
    private String categoria;

    @SerializedName("precio")
    @Expose
    private float precio;

    @SerializedName("delivery")
    @Expose
    private String delivery;

    //**********************************************************************************************
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getImagen4() {
        return imagen4;
    }

    public void setImagen4(String imagen4) {
        this.imagen4 = imagen4;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getCategoria(){
        return categoria;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPreciototal() {
        return getCantidad()*getPrecio();
    }

    public void setPrecioTotal(float precioTotal) {
        this.preciototal = precioTotal;
    }
    //**********************************************************************************************
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        /*if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }*/
        parcel.writeValue(id);
        parcel.writeString(titulo);
        parcel.writeString(descripcion);
        parcel.writeString(imagen1);
        parcel.writeString(imagen2);
        parcel.writeString(imagen3);
        parcel.writeString(imagen4);
        parcel.writeFloat(descuento);
        parcel.writeString(categoria);
        parcel.writeFloat(precio);
        parcel.writeString(delivery);
        parcel.writeFloat(preciototal);
        parcel.writeInt(cantidad);

    }


    public ModelohotSales(Parcel in) {
        /*if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }*/
        //id = in.readInt();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.titulo = in.readString();
        this.descripcion = in.readString();
        this.imagen1 = in.readString();
        this.imagen2 = in.readString();
        this.imagen3 = in.readString();
        this.imagen4 = in.readString();
        this.descuento = in.readFloat();
        this.precio = in.readFloat();
        this.delivery = in.readString();
        this.cantidad = in.readInt();
        this.preciototal = in.readFloat();
    }

    public static final Creator<ModelohotSales> CREATOR = new Creator<ModelohotSales>() {
        @Override
        public ModelohotSales createFromParcel(Parcel in) {
            return new ModelohotSales(in);
        }

        @Override
        public ModelohotSales[] newArray(int size) {
            return new ModelohotSales[size];
        }
    };




}
