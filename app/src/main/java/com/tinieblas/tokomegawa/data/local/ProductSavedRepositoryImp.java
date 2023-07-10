package com.tinieblas.tokomegawa.data.local;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tinieblas.tokomegawa.data.local.base.SharedRepo;
import com.tinieblas.tokomegawa.domain.repository.ProductsSavedRepository;

import java.util.Set;


// Productos Guardados
public class ProductSavedRepositoryImp extends SharedRepo implements ProductsSavedRepository {

    private static final String NAME = "productos_guardados_name";
    private static final String KEY_PRODUCTOS_GUARDADOS = "productos_guardados";

    public ProductSavedRepositoryImp(Context context) {
        super(context, NAME);
    }

    public ProductSavedRepositoryImp(Context context, String KEY_NAME) {
        super(context, KEY_NAME);
    }


    @Override
    public Set<String> getProductosGuardados() {
        return getStringSet(KEY_PRODUCTOS_GUARDADOS);
    }

    @Override
    public void saveProductosGuardados(Set<String> values) {
        putStringSet(KEY_PRODUCTOS_GUARDADOS, values);
    }


    public void addItem(String idProducto){
        Set<String> productosGuardados = getProductosGuardados();
        productosGuardados.add(idProducto);
        saveProductosGuardados(productosGuardados);

    }
    public void removeItem(String idProducto){
        Set<String> productosGuardados = getProductosGuardados();
        if (ifContainsItem(idProducto)) {
            productosGuardados.remove(String.valueOf(idProducto));
        }
    }



    public Boolean ifContainsItem(String idProducto){
        Set<String> productosGuardados = getProductosGuardados();

        return productosGuardados.contains(idProducto);
    }

    public Boolean ifContainsItem(Integer idProducto){
        Set<String> productosGuardados = getProductosGuardados();

        return productosGuardados.contains(String.valueOf(idProducto));
    }




    /**

    Si retorna true entonces se ah eliminado, si retorna false entonces se ah añadido
    */
    public Boolean removeItemOrAdd(String idProducto){
        Set<String> productosGuardados = getProductosGuardados();

        if (productosGuardados.contains(String.valueOf(idProducto))) {
            // Si el producto ya está en la lista, eliminarlo
            productosGuardados.remove(String.valueOf(idProducto));
            saveProductosGuardados(productosGuardados);
            return true;
        } else {
            // Si el producto no está en la lista, agregarlo
            productosGuardados.add(String.valueOf(idProducto));
            saveProductosGuardados(productosGuardados);
            return false;
        }

    }




    /*

    if (productosGuardados.contains(String.valueOf(idProducto))) {
                    // Si el producto ya está en la lista, eliminarlo
                    productosGuardados.remove(String.valueOf(idProducto));
                    Toast.makeText(holder.itemView.getContext(), "Producto eliminado " + producto.getIdProducto(), Toast.LENGTH_SHORT).show();
                    Log.e("productosEliminado: ", productosGuardados.toString());

                } else {
                    // Si el producto no está en la lista, agregarlo
                    productosGuardados.add(String.valueOf(idProducto));
                    Toast.makeText(holder.itemView.getContext(), "Producto guardado " + producto.getIdProducto(), Toast.LENGTH_SHORT).show();
                    Log.e("productosGuardados: ", productosGuardados.toString());
                }

     */


}
