package com.tinieblas.tokomegawa.ui.activities;

import static com.tinieblas.tokomegawa.data.constants.Constants.CODIGO_PERMISOS_UBICACION;
import static com.tinieblas.tokomegawa.data.constants.Constants.KEY_PRODUCTO;
import static com.tinieblas.tokomegawa.data.constants.Constants.PREFS_NAME_CARRITO;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.R;
import com.tinieblas.tokomegawa.databinding.ActivityDetailsBinding;
import com.tinieblas.tokomegawa.databinding.ActivityMyCartBinding;
import com.tinieblas.tokomegawa.databinding.ItemsMycartBinding;
import com.tinieblas.tokomegawa.models.Producto.ProductosItem;
import com.tinieblas.tokomegawa.models.User;
import com.tinieblas.tokomegawa.ui.adptadores.CarritoAdapter;
import com.tinieblas.tokomegawa.utils.NavigationContent;
import com.tinieblas.tokomegawa.utils.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCartActivity extends AppCompatActivity {
    private View decorView;
    private Context context;
    private ActivityMyCartBinding activityMyCartBinding;
    private FirebaseFirestore mFirestore;

    private static final int CODIGO_PERMISOS_UBICACION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyCartBinding = ActivityMyCartBinding.inflate(getLayoutInflater());
        setContentView(activityMyCartBinding.getRoot());
        context = this;

        // Ocultar barra de sistema
        getWindow().getDecorView().setSystemUiVisibility(hideSystemBar());

        obtenerProductosDelCarrito();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        // Obtener la instancia del SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        // Obtener los valores almacenados en el SharedPreferences
        String UID = sharedPreferences.getString("userUid", "");

        getNombreUser(UID);
        System.out.println("my cart UID ===>" + UID);
        gerPermission();
    }


    public void gerPermission() {
        // Verificar si ya se han otorgado los permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Si los permisos ya se han otorgado, realizar las acciones necesarias
            obtenerUbicacion();
            getLocation();
        } else {
            // Si no se han otorgado los permisos, solicitarlos al usuario
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_PERMISOS_UBICACION);
        }
    }

    public void obtenerUbicacion() {
        // Crear una instancia de LocationManager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Verificar si el GPS y la red están habilitados
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            // Obtener la última ubicación conocida del usuario
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Si se encuentra una ubicación, mostrarla
            if (lastKnownLocation != null) {
                double latitud = lastKnownLocation.getLatitude();
                double longitud = lastKnownLocation.getLongitude();

                String latitudString = String.valueOf(latitud);
                String longitudString = String.valueOf(longitud);
                Geocoder geocoder = new Geocoder(MyCartActivity.this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitud, longitud, 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String ciudad = addresses.get(0).getLocality();
                String departamento = addresses.get(0).getAdminArea();

                Log.d("MiUbicacion", "ciudad: " + ciudad + ", departamento: " + departamento);

                // Obtener la instancia del SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MiUbicacion", Context.MODE_PRIVATE);

                // Editar el SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("departamento", ciudad);
                editor.putString("distrito", departamento);
                editor.apply();

            } else {
                // Si no se encuentra una ubicación, solicitar una actualización de ubicación
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        } else {
            // Si el GPS y la red no están habilitados, solicitar al usuario que los active
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitud = location.getLatitude();
            double longitud = location.getLongitude();

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.removeUpdates(locationListener);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    private void getLocation() {
        SharedPreferences sharedPreferences = getSharedPreferences("MiUbicacion", Context.MODE_PRIVATE);
        String departamentoGuardada = sharedPreferences.getString("departamento", "");
        String distritoGuardado = sharedPreferences.getString("distrito", "");

        while (TextUtils.isEmpty(departamentoGuardada) || TextUtils.isEmpty(distritoGuardado)) {
            departamentoGuardada = sharedPreferences.getString("departamento", "");
            distritoGuardado = sharedPreferences.getString("distrito", "");
        }

        activityMyCartBinding.Departamento.setText(departamentoGuardada);
        activityMyCartBinding.Distrito.setText(distritoGuardado + ",");
    }
    @SuppressLint("SetTextI18n")
    private void getNombreUser(String userId) {
        DocumentReference docRef = mFirestore.collection("Usuarios").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String firstName = document.getString(User.KEY_NOMBRES);
                    if (firstName != null && !firstName.isEmpty()) {
                        activityMyCartBinding.textNombre.setText(firstName);
                    } else {
                        activityMyCartBinding.textNombre.setText("default");
                    }
                } else {
                    System.out.println("No such document");
                }
            } else {
                System.out.println("Error getting document: " + task.getException());
            }
        });
    }

    private int hideSystemBar() {
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(i -> {
            if (i == 0) {
                decorView.setSystemUiVisibility(hideSystemBar());
            }
        });
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    public List<ProductosItem> obtenerProductosDelCarrito() {
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);
        String listaProductosJson = sharedPreferences.getString("lista_productos_carrito", "");

        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductosItem>>(){}.getType();
        List<ProductosItem> listaProductosItems = gson.fromJson(listaProductosJson, type);
        mostrarCardsCarrito(listaProductosItems);
        calcularSubTotal();
        return listaProductosItems;
    }

    public void mostrarCardsCarrito(List<ProductosItem> carrito) {
        if (carrito != null && !carrito.isEmpty()) {
            CarritoAdapter mCarritoAdapter = new CarritoAdapter(MyCartActivity.this, carrito, activityMyCartBinding.textSubTotal);
            activityMyCartBinding.RecyclerMyCart.setAdapter(mCarritoAdapter);
            activityMyCartBinding.RecyclerMyCart.setLayoutManager(new LinearLayoutManager(MyCartActivity.this, RecyclerView.VERTICAL, false));
            activityMyCartBinding.viewSwitcher.setDisplayedChild(0);
            activityMyCartBinding.animateView.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(context, "No hay datos en el carrito", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    public void calcularSubTotal() {
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);
        String listaProductosJson = sharedPreferences.getString("lista_productos_carrito", "");

        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductosItem>>() {}.getType();
        List<ProductosItem> listaProductosItems = gson.fromJson(listaProductosJson, type);

        if (listaProductosItems != null && !listaProductosItems.isEmpty()) {
            double subTotal = 0;
            for (ProductosItem producto : listaProductosItems) {
                double precio = producto.getPrecioUnitario() * producto.getAmount();
                subTotal += precio;
            }
            activityMyCartBinding.textSubTotal.setText("S/. " + subTotal);
        } else {
            activityMyCartBinding.textSubTotal.setText("S/. 0");
        }
    }

    public void volver(View view){
        NavigationContent.cambiarActividad(this, MainActivity.class);
    }

    @Override
    public void onBackPressed() {
        // Eliminado el método onBackPressed() ya que no hay ninguna implementación específica para este caso
    }
}
