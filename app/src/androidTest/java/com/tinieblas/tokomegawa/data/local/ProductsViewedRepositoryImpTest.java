package com.tinieblas.tokomegawa.data.local;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.tinieblas.tokomegawa.domain.models.ProductosItem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ProductsViewedRepositoryImpTest {

    private ProductsViewedRepositoryImp repositoryImp;
    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        repositoryImp = new ProductsViewedRepositoryImp(appContext);
    }

    @Test
    public void getAll() {
        System.out.println(repositoryImp.getAll());
    }

    @Test
    public void insertAll() {
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void delete() {
        Integer productId = 1;
        ProductosItem item = repositoryImp.get(productId);
        repositoryImp.deleteAll();
        repositoryImp.insertAll(new ArrayList<ProductosItem>(){{
            add(item);
        }});
        getAll();
        assertEquals(repositoryImp.getAll().size(), 1);
    }

    @Test
    public void get() {
    }

    @Test
    public void save() {
    }
}