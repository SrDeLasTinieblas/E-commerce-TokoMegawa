package com.tinieblas.tokomegawa.ui.adptadores.Modelos;

public class Modelo {

    Integer langLogo;
    String langName;

    public void setLangLogo(Integer langLogo) {
        this.langLogo = langLogo;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

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

    @Override
    public String toString() {
        return "Modelo{" +
                "langLogo=" + langLogo +
                ", langName='" + langName + '\'' +
                '}';
    }
}
