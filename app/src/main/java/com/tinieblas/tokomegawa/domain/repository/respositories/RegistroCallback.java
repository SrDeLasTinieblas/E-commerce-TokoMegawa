package com.tinieblas.tokomegawa.domain.repository.respositories;

public interface RegistroCallback {
    void onRegistroExitoso();
    void onRegistroFallido();
    boolean isRegistroExitoso();

}
