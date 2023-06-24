package com.tinieblas.tokomegawa.utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.mockito.Mock;

public class setCardsCategoriasTest {

/*
    @Mock
    private Ordering.Context context;

    @Mock
    private FragmentActivity activity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(context.getActivity()).thenReturn(activity);
    }

    @Test
    public void testSetCardsFilter() {
        List<ProductosItem> productos = Arrays.asList(
                new ProductosItem(1, "a", "String descripcionProducto", "String imagen1",
                        "String imagen2", "String imagen2", 22.3, 23,
                        "categoria1", "String delivery", 4),

                new ProductosItem(2, "a", "String descripcionProducto", "String imagen1",
                        "String imagen2", "String imagen2", 22.3, 23,
        "categoria2", "String delivery", 4),

        new ProductosItem(3 , "a", "String descripcionProducto", "String imagen1",
                "String imagen2", "String imagen2", 22.3, 23,
                "categoria3", "String delivery", 4)
        );
*/
/**
 * 		this.idProducto = idProducto;
 * 		this.nombreProducto = nombreProducto;
 * 		this.descripcionProducto = descripcionProducto;
 * 		this.imagen1 = imagen1;
 * 		this.imagen2 = imagen2;
 * 		this.imagen3 = imagen3;
 * 		this.precioUnitario = precioUnitario;
 * 		this.cantidadDisponible = cantidadDisponible;
 * 		this.categoria = categoria;
 * 		this.delivery = delivery;
 * 		this.amount = amount;
 *
 * **/
/*
        ArrayList<Modelo> expectedProductosList = new ArrayList<>();
        ArrayList<String> expectedCategorias = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++) {
            ProductosItem producto = productos.get(i);
            String categoria = producto.getCategoria();
            int imageResource = 123; // Simulated image resource
            String nombre = categoria;
            Modelo model = new Modelo(imageResource, nombre);
            expectedProductosList.add(model);

            if (!expectedCategorias.contains(categoria)) {
                expectedCategorias.add(categoria);
            }
        }

        ProductosItem productosItem = new ProductosItem();
        productosItem.setContext(context); // Set the mocked context

        fragmentHome.setCardsFilter(productos);

        verify(activity, times(1)).runOnUiThread(any(Runnable.class));
        assertEquals(expectedProductosList, fragmentHome.getProductosList());
        assertEquals(expectedCategorias, fragmentHome.getCategorias());
    }

    // TODO: Write additional test cases for getImageResourceForCategory() and createRecyclerView() methods

*/
}