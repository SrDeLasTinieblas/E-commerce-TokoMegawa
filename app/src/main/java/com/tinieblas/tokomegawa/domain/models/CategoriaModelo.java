package com.tinieblas.tokomegawa.domain.models;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;

import java.util.List;

public class CategoriaModelo {

    public Integer langLogo;
    public String langName;
    private List<ProductosItem> productosList;

    public void setLangLogo(Integer langLogo) {
        this.langLogo = langLogo;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public CategoriaModelo(Integer langLogo, String langName){
        this.langLogo = langLogo;
        this.langName = langName;
    }
    public List<ProductosItem> getProductosList() {
        return productosList;
    }

    public void setProductosList(List<ProductosItem> productosList) {
        this.productosList = productosList;
    }
    public Integer getLangLogo(){
        return langLogo;
    }

    public String getLangName(){
        return langName;
    }

//    @Override
//    public String toString() {
//        return "Modelo{" +
//                "langLogo=" + langLogo +
//                ", langName='" + langName + '\'' +
//                '}';
//    }


    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
