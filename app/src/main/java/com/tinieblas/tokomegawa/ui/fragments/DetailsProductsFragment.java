package com.tinieblas.tokomegawa.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.constants.Constants;
import com.tinieblas.tokomegawa.respositories.FirebaseData;
import com.tinieblas.tokomegawa.databinding.DetailsProductsFragmentBinding;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class DetailsProductsFragment extends Fragment {

    private ModelohotSales modelohotSales;
    DetailsProductsFragment context;
    FirebaseFirestore mFirestore;
    /*FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;*/
    ArrayList<String> item = new ArrayList<String>();

    private final FirebaseData firebaseData = new FirebaseData();
    String name, apellido;
    private final List<String> ListProductoCarrito = new ArrayList<>();
    public int valor = 1;
    //GridView gridView;
    private final List<ModelohotSales> ListProductos = new ArrayList<>();
    //private SharedPreferences preferences;

    private DetailsProductsFragmentBinding detailsProductsFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = this;
        //getClassModelohotSales();
        detailsProductsFragmentBinding = DetailsProductsFragmentBinding.inflate(inflater, container, false);
        /*preferences = getActivity().getSharedPreferences("clave",
                Context.MODE_PRIVATE);*/
        firebaseData.uploadDataFireBase(getActivity());
        //databaseReference = FirebaseDatabase.getInstance().getReference();
        mFirestore = FirebaseFirestore.getInstance();
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                modelohotSales = result.getParcelable(Constants.INTENT_NAME_MODELO);
                ListProductos.add(modelohotSales);

                detailsProductsFragmentBinding.textTituloProduct.setText(modelohotSales.getTitulo());
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setMovementMethod(new ScrollingMovementMethod());
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setText(modelohotSales.getDescripcion());
                detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.valueOf("S/ " + modelohotSales.getPrecio()));

                getUser();
                GlideImage();
                onClickListener(modelohotSales.getPrecio(), modelohotSales.getPreciototal());

                btnImages(modelohotSales.getImagen1(), modelohotSales.getImagen2(), modelohotSales.getImagen3());
                GuardarOrRemover();

                CargarDatosRecentlyViewed(modelohotSales.getId().toString());
                //AddRecentlyViewed();
            }
        });

        detailsProductsFragmentBinding.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarOrRemover();
            }
        });

        return detailsProductsFragmentBinding.getRoot();

    }

    boolean estaEnSharedPreference = false;
    boolean inCarrito = false;
    private final List<ModelohotSales> ListaRecentlyViewed = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    public void CargarDatosRecentlyViewed(String IDProducto){

        list.add(IDProducto);
        setSharedPreferences(list.iterator().next());

        /**cargarpreferencias();*/

    }

    public void cargarpreferencias() {
        SharedPreferences preferences = requireContext().getSharedPreferences("lista", Context.MODE_PRIVATE);
        Set<String> set = preferences.getStringSet("datos", null);

        //list.clear();
        list.addAll(set);
        //Convert HashSet to List.
        list = new ArrayList<String>(set);
        Toast.makeText(getContext(), ""+list, Toast.LENGTH_SHORT).show();
    }

    public void setSharedPreferences(String datoAguardar){
        SharedPreferences preferences = requireContext().getSharedPreferences("lista", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> set = new HashSet<String>(Collections.singleton(datoAguardar));
        editor.putStringSet("datos", set);
        editor.apply();
    }
    public void AddRecentlyViewed(){
        if(estaEnSharedPreference){
            // Si hay datos lo eliminara
            estaEnSharedPreference = false;
            Iterator<ModelohotSales> itr = ListaRecentlyViewed.iterator();

            // Mientras halla siguiente va seguir la condicion
            while (itr.hasNext()) {
                // Aqui obtenemos el id y lo comparamos con el siguiente id si es igual lo borrara
                if(modelohotSales.getId().equals(itr.next().getId())){
                    itr.remove();
                    //Toast.makeText(getContext(), "Quitado del carrito" , Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } else {
            // sino está lo agregas
            estaEnSharedPreference = true;
            ListaRecentlyViewed.add(modelohotSales);
        }
    }

    public void sendCarrito(){
        GuardarOrRemover();
    }

    boolean beSharedPreferences = false;

    private void GuardarOrRemover(){
        // Si hay datos
        if (beSharedPreferences) {
            // si hay datos
            beSharedPreferences = false;
            //ListProductsCarrito.add(modelohotSales);
            Toast.makeText(context.getContext(), "Añadido al carrito", Toast.LENGTH_SHORT).show();
            /**EnviandoDataToFireBase();*/

            //detailsProductsFragmentBinding.animationView.setMinAndMaxProgress(0f, 0.20f);
            detailsProductsFragmentBinding.animationView.playAnimation();
            //detailsProductsFragmentBinding.animationView.pauseAnimation();


        } else {
            beSharedPreferences = true;
            Toast.makeText(context.getContext(), "Quitado del carrito", Toast.LENGTH_SHORT).show();
            detailsProductsFragmentBinding.animationView.setProgress(0);
            detailsProductsFragmentBinding.animationView.pauseAnimation();
        }
    }

    /** Envia un post a la api y añade un producto en el carrito */
    private void EnviandoDataToBaseData(String Descripcion, String image, String precio, Integer cantidad, String color, String username) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\r\n  \"" +
                "id\": 0,\r\n  \"" +
                "username\": \""+username+"" +
                "image\": \""+image+"" +
                "Descripcion\": \""+Descripcion+"" +
                "precio\": \""+precio+"" +
                "cantidad\": \""+cantidad+"" +
                "color\": \""+color+"" +
                "\"\r\n}");

        okhttp3.Request  request = new okhttp3.Request.Builder()

                .url("http://webapiventareal.somee.com/addCliente")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();

        okhttp3.Response response = client.newCall(request).execute();


        /**
         *
        Map<String, Object> map = new HashMap<>();
        */

        /**
         *             detailsProductsFragmentBinding.imageDetailsProducts1.toString());
         *             detailsProductsFragmentBinding.textPrecioDestailsProductos.getText().toString());
         *             detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.getText().toString());
         *             detailsProductsFragmentBinding.textCantidad.getText().toString());
         */
        /** ArrayList<String> item = new ArrayList<String>( */

        /**item.add(modelohotSales.getImagen1());
        item.add(detailsProductsFragmentBinding.textPrecioDestailsProductos.getText().toString());
        item.add(detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.getText().toString());
        item.add(detailsProductsFragmentBinding.textCantidad.getText().toString());*/

        /**System.out.println("==> item "+item);*/

        /**map.put(detailsProductsFragmentBinding.textTituloProduct.getText().toString(), item);*/

        /**FireStore("Carrito", map);*/

    }

    public void FireStore(String collectionPath, Map<String, Object> map){
        mFirestore.collection(collectionPath).document(getUser()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                //getActivity().finish();
                Toast.makeText(getContext(), "Succesfull", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getnCotext(), getUser(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                System.out.printf(e.toString());
            }
        });
    }

    /**
     *
     " "id": 1, "titulo": "Sony WH/100XM", "descripcion": "The intuitive and intelligent WH-1000XM4 headphones", "imagen1": "https://i.ibb.co/sVYrV4k/Frame-headphone.png", "imagen2": "https://i.ibb.co/sVYrV4k/Frame-headphone.png", "imagen3": "https://i.ibb.co/sVYrV4k/Frame-headphone.png", "imagen4": "", "descuento": 20, "precio": 128, "delivery":"no free shipping""
     * @return
     */
    public String getUser(){
            firebaseData.getDataUser(new EventListener<DocumentSnapshot>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                  name = value.getString("nombres");
                  apellido = value.getString("apellidos");
                }
            });
            return name+ " " + apellido;
    }
    private void purificandoDataProducto() throws IOException {
        List<ModelohotSales> ListProductsCarrito = new ArrayList<>();
        ListProductsCarrito.add(modelohotSales);

        String MyString = new Gson().toJson(ListProductsCarrito)
                .replace("]", "")
                .replace("}", "")
                .replace("[", "")
                .replace("{", "");;

        String[] MyCarrito = MyString.split(",");

        MyCarrito[20] = "PrecioTotal: " +detailsProductsFragmentBinding.textPrecioDestailsProductos.getText().toString();
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    public void GlideImage(){
        Glide.with(detailsProductsFragmentBinding.getRoot())
                .load(modelohotSales.getImagen1())
                .placeholder(R.drawable.button_white)
                .into(detailsProductsFragmentBinding.imageDetailsProducts1);

        detailsProductsFragmentBinding.buttonShipping.setText(modelohotSales.getDelivery());

        Glide.with(detailsProductsFragmentBinding.getRoot())
                .load(modelohotSales.getImagen1())
                .placeholder(R.drawable.button_white)
                .into(detailsProductsFragmentBinding.imageDetailsProducts1A);

        Glide.with(detailsProductsFragmentBinding.getRoot())
                .load(modelohotSales.getImagen2())
                .placeholder(R.drawable.button_white)
                .into(detailsProductsFragmentBinding.imageDetailsProducts2B);

        Glide.with(detailsProductsFragmentBinding.getRoot())
                .load(modelohotSales.getImagen3())
                .placeholder(R.drawable.button_white)
                .into(detailsProductsFragmentBinding.imageDetailsProducts3C);
    }

    public void btnImages(String img1, String img2, String img3){
        detailsProductsFragmentBinding.imageDetailsProducts1A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img1)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });

        detailsProductsFragmentBinding.imageDetailsProducts2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img2)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });

        detailsProductsFragmentBinding.imageDetailsProducts3C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(detailsProductsFragmentBinding.getRoot())
                        .load(img3)
                        .placeholder(R.drawable.button_white)
                        .into(detailsProductsFragmentBinding.imageDetailsProducts1);
            }
        });
    }

    public void onClickListener(float precioTotal, float Total){
        detailsProductsFragmentBinding.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAumentando(precioTotal, Total);
            }
        });

        detailsProductsFragmentBinding.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDisminuyendo(precioTotal);
            }
        });
    }

    public void btnAumentando(float precioTotal, float total){
        modelohotSales.setCantidad(++valor);
        BigDecimal bd = new BigDecimal(modelohotSales.getPreciototal()).setScale(2, RoundingMode.HALF_UP);
        detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
        detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.format("S/%s", bd));
    }

    public void btnDisminuyendo(float precioTotal){
        if (valor > 1){
            modelohotSales.setCantidad(--valor);
            //System.out.println(modelohotSales.getCantidad());

            BigDecimal bd = new BigDecimal(modelohotSales.getPreciototal()).setScale(2, RoundingMode.HALF_UP);
            detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
            detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.format("S/%s", bd));
        }
        else {
            detailsProductsFragmentBinding.textCantidad.setText("1");
        }
    }
}





















