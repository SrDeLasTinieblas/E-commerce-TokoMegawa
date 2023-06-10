package com.tinieblas.tokomegawa.ui.adptadores.Modelos;

public class Modelo {

    Integer langLogo;
    String langName;

    public Modelo(Integer langLogo, String langName){
        this.langLogo = langLogo;
        this.langName = langName;
    }

    public Integer getLangLogo(){
        return langLogo;
    }

    public String getLangName(){
        return langName;
    }

}
