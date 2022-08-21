package com.tinieblas.tokomegawa;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tinieblas.tokomegawa.adptadores.Modelos.ModelohotSales;
import com.tinieblas.tokomegawa.constants.Constants;
import com.tinieblas.tokomegawa.data.FirebaseData;
import com.tinieblas.tokomegawa.databinding.DetailsProductsFragmentBinding;
import com.tinieblas.tokomegawa.databinding.FragmentRegistroBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetailsProductsFragment extends Fragment {

    private ModelohotSales modelohotSales;
    DetailsProductsFragment context;
    FirebaseFirestore mFirestore;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private final FirebaseData firebaseData = new FirebaseData();
    String name, apellido;
    private final List<String> ListProductoCarrito = new ArrayList<>();
    public int valor = 1;
    //GridView gridView;
    private final List<ModelohotSales> ListProductos = new ArrayList<>();

    private DetailsProductsFragmentBinding detailsProductsFragmentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = this;
        //getClassModelohotSales();
        detailsProductsFragmentBinding = DetailsProductsFragmentBinding.inflate(inflater, container, false);

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


                //detailsProductsFragmentBinding.imageDetailsProducts.setImageResource(img1);
                getUser();
                GlideImage();
                onClickListener(modelohotSales.getPrecio(), modelohotSales.getPreciototal());

                btnImages(modelohotSales.getImagen1(), modelohotSales.getImagen2(), modelohotSales.getImagen3());
                try {
                    GuardarOrRemover();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cargarDatosDelCarrito(modelohotSales.getId());
            }
        });

        detailsProductsFragmentBinding.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    GuardarOrRemover();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void sendCarrito() throws IOException {
        GuardarOrRemover();

    }

    boolean beSharedPreferences = false;
    boolean inCarrito = false;
    //private final List<ModelohotSales> ListProductsCarrito = new ArrayList<>();

    private void cargarDatosDelCarrito(Integer id) {
        /*if(preferences.contains("compras")){
            String datos = preferences.getString("compras", "");
            Type typeList = new TypeToken<List<ModelohotSales>>(){}.getType();
            ListProductsCarrito.addAll(new Gson().fromJson(datos,typeList));

            for (ModelohotSales modelo : ListProductsCarrito) {
                if (modelo.getId().equals(id)) {
                    beSharedPreferences = true;
                    inCarrito = inCarrito;
                    valor = this.modelohotSales.getCantidad();
                    break;
                }
            }

        }*/
    }

    private void GuardarOrRemover() throws IOException {
        // Si hay datos
        if (beSharedPreferences) {
            // si hay datos
            beSharedPreferences = false;
            //ListProductsCarrito.add(modelohotSales);
            Toast.makeText(context.getContext(), "Añadido al carrito", Toast.LENGTH_SHORT).show();
            EnviandoDataToFireBase();
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
    ArrayList<String> item = new ArrayList<String>();
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

        item.add(modelohotSales.getImagen1());
        item.add(detailsProductsFragmentBinding.textPrecioDestailsProductos.getText().toString());
        item.add(detailsProductsFragmentBinding.textViewDescripcionDetailsProducts.getText().toString());
        item.add(detailsProductsFragmentBinding.textCantidad.getText().toString());

        if (detailsProductsFragmentBinding.textTituloProduct.getText().toString() == modelohotSales.getTitulo()){
            map.put(detailsProductsFragmentBinding.textTituloProduct.getText().toString(), item);
        }


        map.put("item 2 ","item 2222222");

        //map1.put("map2","item");

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

            mFirestore.collection("Carrito").document(getUser()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
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





















