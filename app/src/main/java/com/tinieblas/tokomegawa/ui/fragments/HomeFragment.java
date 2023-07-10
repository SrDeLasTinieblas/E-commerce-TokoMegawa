package com.tinieblas.tokomegawa.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.tinieblas.tokomegawa.data.local.ProductSavedRepositoryImp;
import com.tinieblas.tokomegawa.data.local.ProductsViewedRepositoryImp;
import com.tinieblas.tokomegawa.data.remote.ApiRepositoryImp;
import com.tinieblas.tokomegawa.ui.adptadores.CategoriasAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.FragmentHomeBinding;
import com.tinieblas.tokomegawa.domain.models.CategoriaModelo;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosCategorias;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.ui.activities.MyCartActivity;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosAdapter;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosVistosAdapter;
import com.tinieblas.tokomegawa.utils.AppExecutors;
import com.tinieblas.tokomegawa.utils.NavigationContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private CategoriasAdapter categoriaAdapter;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private PopupWindow popupWindow;
    private ProductosAdapter productosAdapter;

    private ProductosCategorias productosCategorias;
    private boolean isCategoriaSeleccionada = false;
    private boolean isCheckedMasPopulares = false;
    private boolean isCheckedFavoritos = false;
    private final List<ProductosItem> productosListOriginal = new ArrayList<>();
    private final Integer[] langLogo = new Integer[]{R.drawable.earphones, R.drawable.alexa, R.drawable.audifonos,
            R.drawable.camaras, R.drawable.sillas_gamer, R.drawable.tablets, R.drawable.celurales};
    private ProductSavedRepositoryImp repositoryFavoritos;
    private FragmentHomeBinding fragmentHomeBinding;
    boolean EstaEnFavoritos = false;

    private ProductsViewedRepositoryImp viewedRepositoryImp;
    private ApiRepositoryImp apiRepositoryImp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        this.viewedRepositoryImp = new ProductsViewedRepositoryImp(requireContext());
        this.repositoryFavoritos = new ProductSavedRepositoryImp(requireContext());
        this.apiRepositoryImp = new ApiRepositoryImp(requireContext());

        initViews();
        setListeners();
        fetchData();


        return fragmentHomeBinding.getRoot();
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragmentHomeBinding.cardFilterRecyclerView.setLayoutManager(linearLayoutManager2);
        fragmentHomeBinding.cardFilterRecyclerView.setItemAnimator(new DefaultItemAnimator());


        productosAdapter = new ProductosAdapter(getContext(), productosListOriginal);
        fragmentHomeBinding.reciclerViewHotSales.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        fragmentHomeBinding.reciclerViewHotSales.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.reciclerViewHotSales.setAdapter(productosAdapter);
        fragmentHomeBinding.reciclerViewRecently.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void setListeners() {

        fragmentHomeBinding.fab.setOnClickListener(view -> NavigationContent.cambiarActividad(getContext(), MyCartActivity.class));

        fragmentHomeBinding.buttonFiltrar.setOnClickListener(view -> Showfiltro(view));


        fragmentHomeBinding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrarTitulo(editable.toString());
                //fragmentHomeBinding.editTextSearch.setCursorVisible(false);
                //fragmentHomeBinding.editTextSearch.setFocusable(false);

            }
        });
    }

    private void fetchData() {

        new AppExecutors().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                List<ProductosItem> productosItemsFromRepo = apiRepositoryImp.fetchProductos();
                productosListOriginal.addAll(productosItemsFromRepo);

                requireActivity().runOnUiThread(() -> {
                    addCards(productosItemsFromRepo);
                    setCardsFilter(productosItemsFromRepo);
                    //buttonLike(productos);
                });
            }
        });

    }

    private void buttonLike(List<ProductosItem> productos) {
        //ProductosAdapter adapter = new ProductosAdapter(getContext(), productos, fragmentHomeBinding.reciclerViewHotSales);
        productosAdapter = new ProductosAdapter(getContext(), productos);

        new AppExecutors().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                List<ProductosItem> productosItemsFromRepo = apiRepositoryImp.fetchProductos();
                productosListOriginal.addAll(productosItemsFromRepo);

                requireActivity().runOnUiThread(() -> {
                    addCards(productosItemsFromRepo);
                    setCardsFilter(productosItemsFromRepo);
                    //buttonLike(productos);
                });
            }
        });

    }



    }

    private void setCardsFilter(List<ProductosItem> productos) {
        ArrayList<CategoriaModelo> productosList = new ArrayList<>();
        ArrayList<String> categorias = new ArrayList<>();

        for (ProductosItem producto : productos) {
            String categoria = producto.getCategoria();
            int imageResource = getImageResourceForCategory(categoria, langLogo);
            String nombre = categoria;
            CategoriaModelo model = new CategoriaModelo(imageResource, nombre);
            productosList.add(model);

            if (!categorias.contains(categoria)) {
                categorias.add(categoria);
            }
        }

        //Log.e("Lista de categorias ",productosList.toString());
        //Log.e("categorias",categorias.toString());
        createRecyclerView(productosList, categorias);
    }

    private int getImageResourceForCategory(String categoria, Integer[] langLogo) {
        String categoriaMinuscula = categoria.toLowerCase().replace(" ", "_");

        for (Integer logo : langLogo) {
            String nombreRecurso = getResources().getResourceEntryName(logo).toLowerCase();
            if (categoriaMinuscula.equals(nombreRecurso)) {
                return logo;
            }
        }

        return R.drawable.place_holder;
    }


    private CategoriaModelo categoriaModeloSeleccionada = null;

    private void createRecyclerView(ArrayList<CategoriaModelo> productosList,
                                    ArrayList<String> categorias) {
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager
                (requireActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false);

        fragmentHomeBinding.cardFilterRecyclerView.setLayoutManager(linearLayoutManager2);
        fragmentHomeBinding.cardFilterRecyclerView.setItemAnimator(new DefaultItemAnimator());

        categoriaAdapter = new CategoriasAdapter(requireContext(),
                categorias,
                productosList,
                categoria -> {


                    if (categoriaModeloSeleccionada == null) {
                        this.categoriaModeloSeleccionada = categoria;
                        filterProductosByCategoria(categoria);
                    } else if (categoriaModeloSeleccionada.langName == categoria.langName) {

                    if(categoriaModeloSeleccionada == null){
                        this.categoriaModeloSeleccionada = categoria;
                        filterProductosByCategoria(categoria);
                    } else if(categoriaModeloSeleccionada.langName == categoria.langName) {

                        // Eso esta demas porque ya esta filtrado
                        this.categoriaModeloSeleccionada = null;
                        productosAdapter.setProductosList(productosListOriginal);
                    } else {
                        this.categoriaModeloSeleccionada = categoria;
                        filterProductosByCategoria(categoria);
                    }

//                    if (isCategoriaSeleccionada) {
//                        this.categoriaModeloSeleccionada = categoria;
//                        // Restablecer el estado para mostrar todos los productos
//
//                        isCategoriaSeleccionada = false;
//                        productosAdapter.setProductosList(todosLosProductos);
//                        //Toast.makeText(requireActivity(), "Categorias ==> " + categoria, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(), "if" + isCategoriaSeleccionada, Toast.LENGTH_SHORT).show();
//                    } else {
//                        // Filtrar los productos por la categoría seleccionada
//                        filterProductosByCategoria(categoria);
//                        //Toast.makeText(getContext(), categoria.getLangName(), Toast.LENGTH_SHORT).show();
//                        isCategoriaSeleccionada = true;
//                    }

                });


        fragmentHomeBinding.cardFilterRecyclerView.setAdapter(categoriaAdapter);
    }

    private void filterProductosByCategoria(CategoriaModelo categoria) {


        String tag = "FILTER_BY_CATEGORY";

        Log.e(tag, "Categoria \n" + categoria.toString());

        Log.e(tag, "Categoria \n"+categoria.toString());


        // Obtener la categoría seleccionada
        String categoriaSeleccionada = categoria.getLangName();

        // Filtrar los productos por la categoría seleccionada
        ArrayList<ProductosItem> productosFiltrados = new ArrayList<>();
        for (ProductosItem producto : productosListOriginal) {
            if (producto.getCategoria().equals(categoriaSeleccionada)) {
                productosFiltrados.add(producto);
            }
        }

        Log.e(tag, productosFiltrados.toString());
        // Actualizar el adaptador del RecyclerView de productos con la lista filtrada
        productosAdapter.setProductosList(productosFiltrados);
    }

    public void Showfiltro(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            isCheckedMasPopulares = false; // Reiniciar el estado al cerrar el popup

        } else {
            View popupView = LayoutInflater.from(requireActivity()).inflate(R.layout.popup, null);
            //View popupView = getLayoutInflater().inflate(R.layout.popup, null);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // Obtener una referencia al botón button
            Button CheckMasPopulares = popupView.findViewById(R.id.buttonCheck);
            Button CheckFavoritos = popupView.findViewById(R.id.buttonCheck2);

            // Actualizar el fondo del botón según el estado actual
            if (isCheckedMasPopulares) {
                CheckMasPopulares.setBackgroundResource(R.drawable.check1);
            }

            // Agregar el evento onClick al botón button2
            CheckMasPopulares.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isCheckedMasPopulares = !isCheckedMasPopulares; // Alternar el estado al hacer clic

                    if (isCheckedMasPopulares) {
                        CheckMasPopulares.setBackgroundResource(R.drawable.check1);
                        Toast.makeText(getContext(), "Populares check", Toast.LENGTH_SHORT).show();
                    } else {
                        // Aquí puedes establecer el fondo deseado cuando no está marcado
                        CheckMasPopulares.setBackgroundResource(R.drawable.check);
                    }

                    // Aquí puedes realizar otras acciones según el estado
                }
            });

            CheckFavoritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isCheckedFavoritos = !isCheckedFavoritos; // Alternar el estado al hacer clic

                    if (isCheckedFavoritos) {
                        CheckFavoritos.setBackgroundResource(R.drawable.check1);
                        Toast.makeText(getContext(), "favoritos check", Toast.LENGTH_SHORT).show();

                    } else {
                        // Aquí puedes establecer el fondo deseado cuando no está marcado
                        CheckFavoritos.setBackgroundResource(R.drawable.check);
                    }

                    // Aquí puedes realizar otras acciones según el estado
                }
            });

            RangeSlider rangeSlider = popupView.findViewById(R.id.rangeSlider);
            rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NonNull RangeSlider slider) {

                }

                @Override
                public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                    List<Float> values = slider.getValues();
                    float desde = values.get(0);
                    float hasta = values.get(1);
                    filtrarPrecio(desde, hasta);
                }
            });
            popupWindow.showAsDropDown(view, 0, 0);
        }
    }

    public void verificarProductoFavoritos(ProductosItem producto) {
        // Obtener la lista actual de productos guardados en SharedPreferences
        Set<String> productosGuardados = repositoryFavoritos.getProductosGuardados();

        int idProducto = producto.getIdProducto();

        // Verificar si el producto ya está en la lista
        if (productosGuardados.contains(String.valueOf(idProducto))) {
            // Si el producto ya está en la lista, eliminarlo
            productosGuardados.remove(String.valueOf(idProducto));

            //actualizarAparienciaBotonFavorito(fragmentHomeBinding.animationView, EstaEnFavoritos);

            EstaEnFavoritos = true;
        } else {
            // Si el producto no está en la lista, agregarlo
            productosGuardados.add(String.valueOf(idProducto));
            //actualizarAparienciaBotonFavorito(activityDetailsBinding.animationView, EstaEnFavoritos);
            EstaEnFavoritos = false;
        }

        // Guardar la lista actualizada en SharedPreferences
        repositoryFavoritos.saveProductosGuardados(productosGuardados);
        Log.e("repositoryFavoritos: ", productosGuardados.toString());

    }

    private void actualizarAparienciaBotonFavorito(LottieAnimationView button,
                                                   boolean productoGuardado) {
        if (productoGuardado) {
            // Producto guardado: establecer apariencia deseada
            button.setBackgroundResource(R.drawable.vector_ilike1);
            Toast.makeText(getContext(), "id ==> " + productoGuardado + "Esta en favoritos.", Toast.LENGTH_SHORT).show();

        } else {
            // Producto no guardado: establecer apariencia deseada
            button.setBackgroundResource(R.drawable.vector_ilike);
            Toast.makeText(getContext(), "id ==> " + productoGuardado + "No esta en favoritos.", Toast.LENGTH_SHORT).show();

        }
    }

    private void filtrarPorFavoritos() {
        /*List<ProductosItem> favoritosList = repositoryCart.getAll(); // Obtener la lista de favoritos desde el repositorio

        List<ProductosItem> filtrarLista = new ArrayList<>();
        for (ProductosItem item : productosListOriginal) {
            if (favoritosList.contains(item)) {
                filtrarLista.add(item);
            }
        }
        productosAdapter.setProductosList(filtrarLista);*/
    }


    private void filtrarTitulo(String texto) {
        List<ProductosItem> listaFiltrar = new ArrayList<>();
        for (ProductosItem item : productosListOriginal) {
            if (item.getNombreProducto().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrar.add(item);
            }

        }
        //productosAdapter.differ.submitList(new ArrayList<>(listaFiltrar));
        productosAdapter.setProductosList(listaFiltrar);
        Log.e("listaFiltrar", listaFiltrar.toString());
        Log.e("productosListOriginal", productosListOriginal.toString());
    }

    private void filtrarPrecio(float desde, float hasta) {
        List<ProductosItem> filtrarLista = new ArrayList<>();
        for (ProductosItem item : productosListOriginal) {
            if (desde <= item.getPrecioUnitario() && item.getPrecioUnitario() <= hasta) {
                filtrarLista.add(item);
            }
        }
        productosAdapter.setProductosList(filtrarLista);
    }

    @Override
    public void onResume() {
        super.onResume();
        productosAdapter.notifyDataSetChanged();
        getListaProductoVistos();
    }


    private void addCards(List<ProductosItem> productosItemList) {
        // Actualizar la lista de productos destacados
        productosAdapter.setProductosList(productosItemList);
    }

    private void getListaProductoVistos() {

        List<ProductosItem> productosVistos = viewedRepositoryImp.getAll();
        if (productosVistos.isEmpty()) {
            // La lista de productos vistos es nula
            Log.d("HomeFragment", "La lista de productos vistos es nula");
            return;
        }
        Log.d("HomeFragment", "Vistos\n" + productosVistos.toString());
        Collections.reverse(productosVistos);

        // Actualizar el adaptador de productos vistos recientemente
        ProductosVistosAdapter productosVistosAdapter = new ProductosVistosAdapter(getContext(), productosVistos);
        fragmentHomeBinding.reciclerViewRecently.setAdapter(productosVistosAdapter);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, fragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentHomeBinding = null;
    }

    public void SignOut(View view) {
        mAuth.signOut(); // Cerrar sesión
        System.out.println("SignOut");
    }

    @Override
    public void onClick(View view) {
        // Aquí puedes manejar los eventos de clic si es necesario
    }

}
