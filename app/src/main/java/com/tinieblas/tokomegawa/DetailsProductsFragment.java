package com.tinieblas.tokomegawa;

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
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.constants.Constants;
import com.tinieblas.tokomegawa.data.FirebaseData;
import com.tinieblas.tokomegawa.databinding.DetailsProductsFragmentBinding;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

                //ModelohotSales modelohotSales;
                modelohotSales = result.getParcelable(Constants.INTENT_NAME_MODELO);
                ListProductos.add(modelohotSales);
                /*String titulo = result.getString("titulo");
                String descripcion = result.getString("descripcion");
                img1 = result.getString("img1");
                img2 = result.getString("img2");
                img3 = result.getString("img3");
                String img4 = result.getString("img4");
                float descuento = result.getFloat("descuento");
                float precio = result.getFloat("precio");
                String delivery = result.getString("delivery");
                int cantidad = result.getInt("cantidad");
                float PrecioTotal = result.getFloat("PrecioTotal");*/

                detailsProductsFragmentBinding.textTituloProduct.setText(modelohotSales.getTitulo());
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setMovementMethod(new ScrollingMovementMethod());
                detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.setText(modelohotSales.getDescripcion());
                detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.valueOf("S/ " + modelohotSales.getPrecio()));
                //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.valueOf("S/ "+modelohotSales.getPreciototal()));
                //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
                //detailsProductsFragmentBinding.textCantidad.setText(cantidad);


                //Toast.makeText(getContext(), "==> "+ListProductos, Toast.LENGTH_SHORT).show();
                //detailsProductsFragmentBinding.imageDetailsProducts.setImageResource(img1);
                getUser();
                GlideImage();
                onClickListener(modelohotSales.getPrecio(), modelohotSales.getPreciototal());

                btnImages(modelohotSales.getImagen1(), modelohotSales.getImagen2(), modelohotSales.getImagen3());
                GuardarOrRemover();
                /*preferences = getActivity().getSharedPreferences("clave",
                        Context.MODE_PRIVATE);*/
                CargarDatosRecentlyViewed(modelohotSales.getId().toString());
                AddRecentlyViewed();
                //VerificaSiEstaEnElCarrito(22);
            }
        });

        detailsProductsFragmentBinding.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarOrRemover();
            }
        });


        //onClickListener(valor/*, precio*/);

        /*gridView = detailsProductsFragmentBinding.getRoot().findViewById(R.id.gridViewProducts);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context.getContext(), DetailsProductsFragment.class);

                intent.putExtra("ModelohotSales", ListProductos.get(i));
                startActivity(intent);
            }
        });*/
        ;
        //preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        //preferences = context.getActivity().getSharedPreferences("compras", Context.MODE_PRIVATE);

        return detailsProductsFragmentBinding.getRoot();

    }

    boolean estaEnSharedPreference = false;
    boolean inCarrito = false;
    private final List<ModelohotSales> ListaRecentlyViewed = new ArrayList<>();
    private List<String> list = new ArrayList<>();

    public void CargarDatosRecentlyViewed(String IDProducto){
        //Toast.makeText(getContext(), "=> " + IDProducto, Toast.LENGTH_SHORT).show();
        // si hay datos

        //list.addAll(Collections.singleton(IDProducto));
        list.add(IDProducto);
        setSharedPreferences(list.iterator().next());
        cargarpreferencias();
        //Toast.makeText(getContext(), ""+getSharedPreferences("nombre"), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), ""+getSharedPreferences("RecentlyViewed"), Toast.LENGTH_SHORT).show();
        //SharedPreferences preferences = getActivity().getSharedPreferences(nombreDelSharedPreferences, Context.MODE_PRIVATE);
        //Toast.makeText(getContext(), ""+preferences.getString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), ""+ preferences  , Toast.LENGTH_SHORT).show();
        //if(preferences.contains("clave") /*preferences.contains("RecentlyViewed")*/){
        //    // cargamos la lista entonces
        //    String datos = preferences.getString("clave","");
        //    Log.d("clave", datos);

            /*Type typeList = new TypeToken<List<ModelohotSales>>(){}.getType();
            ListaRecentlyViewed.addAll(new Gson().fromJson(datos,typeList));
            //Toast.makeText(getContext(), "datos " + datos, Toast.LENGTH_SHORT).show();

            // Con el foreach recorremos la lista de datos
            for (ModelohotSales modelohotSales : ListaRecentlyViewed){
                // Aqui comparamos los IDs si son iguales es true
                if(modelohotSales.getId().equals(IDProducto)){
                    estaEnSharedPreference = true;
                    inCarrito = true;
                    modelohotSales = modelohotSales;
                    valor = this.modelohotSales.getCantidad();
                    //Toast.makeText(getContext(), "Si esta en las preferencias", Toast.LENGTH_SHORT).show();
                    break;
                }
            }*/
            //updateBackGround(estaEnSharedPreference);

        //}else{
        //    //Toast.makeText(getContext(), "No esta en las preferencias", Toast.LENGTH_SHORT).show();
        //    Log.d("clave", "no hay datos");
        //}

    }

    /*public String getSharedPreferences(String nombreDelSharedPreferences){
        SharedPreferences preferences = getActivity().getSharedPreferences(nombreDelSharedPreferences, Context.MODE_PRIVATE);
        return preferences.getString("dato","404");
    }*/
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

        /*SharedPreferences sharedPreferences = getActivity().getSharedPreferences(nombreDelSharedPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("dato",datoAguardar.size());// dato a guardar
        editor.commit();
        editor.apply();*/

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
            //setSharedPreferences("Lista",ListaRecentlyViewed.toString());
            //Toast.makeText(getContext(), "Añadido al carrito" , Toast.LENGTH_SHORT).show();
            //String datos = preferences.getString("RecentlyViewed","");
            //Toast.makeText(getContext(), "datos: "+ datos, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendCarrito(){
        GuardarOrRemover();
    }

    boolean beSharedPreferences = false;

    //private final List<ModelohotSales> ListProductsCarrito = new ArrayList<>();

    private void GuardarOrRemover(){
        // Si hay datos
        if (beSharedPreferences) {
            // si hay datos
            beSharedPreferences = false;
            //ListProductsCarrito.add(modelohotSales);
            Toast.makeText(context.getContext(), "Añadido al carrito", Toast.LENGTH_SHORT).show();
            try {
                EnviandoDataToFireBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //detailsProductsFragmentBinding.animationView.setMinAndMaxProgress(0f, 0.20f);
            detailsProductsFragmentBinding.animationView.playAnimation();
            //detailsProductsFragmentBinding.animationView.pauseAnimation();


        } else {
            beSharedPreferences = true;

            Toast.makeText(context.getContext(), "Quitado del carrito", Toast.LENGTH_SHORT).show();

            detailsProductsFragmentBinding.animationView.setProgress(0);
            detailsProductsFragmentBinding.animationView.pauseAnimation();

            //detailsProductsFragmentBinding.animationView.playAnimation();
        }
    }

    boolean estaEnFireStore= false;
    public void VerificaSiEstaEnElCarrito(Integer IDProducto){
        String usuario = getUser();
        Toast.makeText(getContext(), getUser(), Toast.LENGTH_SHORT).show();
        /*DocumentReference docRef = mFirestore.collection("Carrito").document(usuario);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d("dd", "DocumentSnapshot data: " + document.getData());
                        Toast.makeText(getContext(), "DocumentSnapshot data: " +
                                document.getData(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No such document" +
                                document.getData(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        /*Task<DocumentSnapshot> task = null;
        DocumentSnapshot document = task.getResult();
        if(document.exists()) {
            Toast.makeText(getContext(), "DocumentSnapshot data: " +
                    document.getData(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "No such document" +
                    document.getData(), Toast.LENGTH_SHORT).show();
        }*/
    }

    private void EnviandoDataToFireBase(/*,String Descripcion, String image, String precio, Integer cantidad String color, String username*/) throws IOException {
            //String id = firebaseAuth.getCurrentUser().getUid();
         //map = detailsProductsFragmentBinding.textPrecioDestailsProductos.getText().toString();
            Map<String, Object> map = new HashMap<>();
            //Map<String, Object> map1 = new HashMap<>();
        /**
         *             detailsProductsFragmentBinding.imageDetailsProducts1.toString());
         *             detailsProductsFragmentBinding.textPrecioDestailsProductos.getText().toString());
         *             detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.getText().toString());
         *             detailsProductsFragmentBinding.textCantidad.getText().toString());
         */
        //ArrayList<String> item = new ArrayList<String>();
        //ArrayList<String> item = new ArrayList<String>();

        item.add(modelohotSales.getImagen1());
        item.add(detailsProductsFragmentBinding.textPrecioDestailsProductos.getText().toString());
        item.add(detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.getText().toString());
        item.add(detailsProductsFragmentBinding.textCantidad.getText().toString());

        //Classproductos classproductos = new Classproductos();
        //classproductos.setTitulo(detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.getText().toString());

        System.out.println("==> item "+item);
        //System.out.println("==> classproductos "+ classproductos.getTitulo());

        /*if (detailsProductsFragmentBinding.textTituloProduct.getText().toString() == modelohotSales.getTitulo()){

        }*/

        //saveSharedPreferencs(item);
        map.put(detailsProductsFragmentBinding.textTituloProduct.getText().toString(), item);

        //SharedPreferences preferencias= getActivity().getSharedPreferences("datos2",Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor=preferencias.edit();
        //String dato = preferences.getString("dato2","");
        //System.out.println(dato);

            /*map.put(detailsProductsFragmentBinding.textTituloProduct.getText().toString(), detailsProductsFragmentBinding.textPrecioDestailsProductos.getText().toString());
            map.put(detailsProductsFragmentBinding.textTituloProduct.getText().toString(), detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.getText().toString());
            map.put(detailsProductsFragmentBinding.textTituloProduct.getText().toString(), detailsProductsFragmentBinding.textCantidad.getText().toString());*/

        //purificandoDataProducto();
                //map.put("id", id);
                /*map.put("nombres", nombres);
                map.put("apellidos", apellidos);
                map.put("direccion", direccion);
                map.put("edad", edad);
                map.put("mail", mail);
                map.put("password", password);
                map.put("username", username);*/
        //ListProductoCarrito.add("descripcion");

        //Toast.makeText(getContext(), getUser()+ new Gson().toJson(ListProductsCarrito) +" <==Usuario", Toast.LENGTH_SHORT).show();

        //System.out.println(modelohotSales.getPreciototal());
        //System.out.println("lenght "+MyCarrito[20]);

        //MyCarrito.toString().replaceAll("","");

        //Log.e("carrito",getUser()+ new Gson().toJson(ListProductsCarrito) +" <==Usuario" );
            //sweetAlertDialog.sweetAlertLoading(this);

        FireStore("Carrito", map);

            /*mFirestore.collection("Carrito").document(getUser()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    //getActivity().finish();
                    Toast.makeText(getContext(), "Succesfull", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    System.out.printf(e.toString());
                }
            });*/
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

    public void saveSharedPreferencs(List<String> item){
        SharedPreferences preferencias= getActivity().getSharedPreferences("datos2",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("dato5", String.valueOf(item));
        editor.commit();
        getActivity().finish();

    }


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

    ArrayList<JSONObject> listResults = new ArrayList<JSONObject>();

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


        /*for (int i = 0; i < ListProductsCarrito.size(); i++) {
            JSONObject otroObject = listResults.get(i);

            listResults.add(otroObject);
        }
        JSONObject[] jsonsFinally= new JSONObject[listResults.size()];
        listResults.toArray(jsonsFinally);

        System.out.println(jsonsFinally);*/

        /*Json json = new Json();

        JsonNode node = Json.parse(new Gson().toJson(ListProductsCarrito));

        System.out.println("node "+node.asText());*/

        /*int tamanhioArray = arrayJSON.length();
        ArrayList<JSONObject> listResults = new ArrayList<JSONObject>();
        for (int i = 0; i < tamanhioArray; i++) {
            JSONObject otroObject = listResults.getJSONObject(i);

            listResults.add(otroObject);
        }

        JSONObject[] jsonsFinally= new JSONObject[listResults.size()];
        listResults.toArray(jsonFinally);*/
        /*JSONObject json = new JSONObject((Map) ListProductsCarrito);
        JSONArray arrayJSON = myjson.getJSONArray("Results");*/
        //System.out.println("==> MyCarrito "+ Arrays.toString(MyCarrito));
        //return Arrays.toString(MyCarrito);
    }

    private boolean CheckCartData(Integer idCarrito){
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (getArguments() != null && getArguments().containsKey(ModelohotSales.Parcel)){
            ModelohotSales modelohotSales = getArguments().getParcelable(ModelohotSales.Parcel);
            detailsProductsFragmentBinding.textCantidad.setText(modelohotSales.getCantidad());
        }*/
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
        //System.out.println(modelohotSales.getCantidad());

        BigDecimal bd = new BigDecimal(modelohotSales.getPreciototal()).setScale(2, RoundingMode.HALF_UP);
        detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
        detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.format("S/%s", bd));

        //Intent intent = new Intent(context.getContext(), ModelohotSales.class);
        //intent.putExtra("PrecioTotal", precioTotal);
        //System.out.println(total);
        //detailsProductsFragmentBinding.textCantidad.setText(s);
        //System.out.println(++valor);
        //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
        //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(("S/"+(modelohotSales.getPrecioTotal())));

    }

    public void btnDisminuyendo(float precioTotal){
        if (valor > 1){
            modelohotSales.setCantidad(--valor);
            //System.out.println(modelohotSales.getCantidad());

            BigDecimal bd = new BigDecimal(modelohotSales.getPreciototal()).setScale(2, RoundingMode.HALF_UP);
            detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
            detailsProductsFragmentBinding.textPrecioDestailsProductos.setText(String.format("S/%s", bd));
            //modelohotSales.setCantidad(--valor);
            //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
            //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText("S/"+(modelohotSales.getPrecioTotal()));
        }
        else {
            detailsProductsFragmentBinding.textCantidad.setText("1");
            //detailsProductsFragmentBinding.textCantidad.setText(String.valueOf(modelohotSales.getCantidad()));
            //detailsProductsFragmentBinding.textPrecioDestailsProductos.setText("S/"+(modelohotSales.getPrecioTotal()));

        }

    }

    private void getClassModelohotSales(){
        //Bundle bundle = getActivity().getIntent().getExtras();
        //modelohotSales = bundle.getParcelable(ModelohotSales.Parcel);

        /*Bundle bundle = this.getArguments();
        if (bundle != null) {
            modelohotSales = bundle.getParcelable("modelohotSales");
        }*/
        /*if (getArguments() != null && getArguments().containsKey(ModelohotSales.Parcel)){
            ModelohotSales modelohotSales = getArguments().getParcelable(ModelohotSales.Parcel);
            detailsProductsFragmentBinding.textCantidad.setText(modelohotSales.getCantidad());

        }*/

    }

}





















