package com.tinieblas.tokomegawa.data.local;

import static org.junit.Assert.*;
import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

public class ProductSavedRepositoryImpTest {


    private ProductSavedRepositoryImp repositoryImp;
    private static final String NAME = "productos_guardados_name_test";

    @Before
    public void setup(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        repositoryImp = new ProductSavedRepositoryImp(appContext);
    }

    @Test
    public void addItem(){
        String productId = "40";
        repositoryImp.addItem(productId);
        System.out.println(repositoryImp.getProductosGuardados());
        assertEquals(repositoryImp.getProductosGuardados().size(), 1);
    }


    @Test
    public void addItems(){
        repositoryImp.addItem("40");
        repositoryImp.addItem("41");

        System.out.println(repositoryImp.getProductosGuardados());
        assertEquals(repositoryImp.getProductosGuardados().size(), 1);
    }

    @Test
    public void remove(){
        String productId = "50";
        repositoryImp.addItem(productId);
        repositoryImp.removeItem(productId);
        assertEquals(repositoryImp.getProductosGuardados().size(), 0);
    }



    @Test
    public void checkAllItems(){
        System.out.println(repositoryImp.getProductosGuardados());
    }

    @Test
    public void removeItemOrAdd(){
        String productId = "653";
        // TODO Si retorna true entonces se ah eliminado, si retorna false entonces se ah añadido
        assertEquals(repositoryImp.removeItemOrAdd(productId), false);
    }

    @Test
    public void removeItemOrAdd2(){
        String productId = "653";
        repositoryImp.addItem(productId);
        // TODO Si retorna true entonces se ah eliminado, si retorna false entonces se ah añadido
        assertEquals(repositoryImp.removeItemOrAdd(productId), true);
    }


    @Test
    public void ifContainsItem(){
        String productId = "3213";
        repositoryImp.addItem(productId);
        assertEquals(repositoryImp.ifContainsItem(productId), false);

    }






}