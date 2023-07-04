package com.tinieblas.tokomegawa.ui.activities;

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
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.tinieblas.tokomegawa.databinding.ActivityMyCartBinding;
import com.tinieblas.tokomegawa.domain.models.ProductosItem;
import com.tinieblas.tokomegawa.ui.adptadores.CarritoAdapter;
import com.tinieblas.tokomegawa.ui.adptadores.ProductosAdapter;
import com.tinieblas.tokomegawa.utils.NavigationContent;
import com.tinieblas.tokomegawa.utils.hideMenu;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCartActivity extends AppCompatActivity {
    private View decorView;
    private Context context;
    private ActivityMyCartBinding activityMyCartBinding;
    private CarritoAdapter mCarritoAdapter;
    private List<ProductosItem> carritoList = new ArrayList<>();
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

        setupFirebase();
        setupSharedPreferences();
        getPermission();

        activityMyCartBinding.btnBorrarCarrito.setVisibility(View.VISIBLE);

        //obtenerProductosDelCarrito(); // Llamar a la función para mostrar las tarjetas del carrito
        initViews(); // Configurar el RecyclerView
    }

    private void initViews() {
        mCarritoAdapter = new CarritoAdapter(MyCartActivity.this, carritoList,
                activityMyCartBinding.textSubTotal, activityMyCartBinding.textTotal,
                activityMyCartBinding.RecyclerMyCart,
                activityMyCartBinding.viewSwitcher
        );

        activityMyCartBinding.RecyclerMyCart.setAdapter(mCarritoAdapter);
        activityMyCartBinding.RecyclerMyCart.setLayoutManager(new LinearLayoutManager(MyCartActivity.this, RecyclerView.VERTICAL, false));

        obtenerProductosDelCarrito(); // Llamar a la función después de inicializar el adaptador
        mCarritoAdapter.setCarrito(obtenerProductosDelCarrito());


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCarritoAdapter.itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(activityMyCartBinding.RecyclerMyCart);


    }

    // Función para configurar Firebase Firestore
    private void setupFirebase() {
        //FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    // Función para configurar SharedPreferences
    private void setupSharedPreferences() {
        // Obtener la instancia del SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        // Obtener los valores almacenados en el SharedPreferences
        String UID = sharedPreferences.getString("userUid", "");

        //getNombreUser(UID);
        System.out.println("my cart UID ===>" + UID);
    }

    // Función para ocultar la barra de sistema
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

    // Función para borrar todos los productos del carrito
    public void BorrarTodosLosProductosDelCarrito(View view) {
        // Configurar la animación de Lottie y ejecutarla
        //animationView.setAnimation(R.raw.loading_animation);
        //animationView.playAnimation();

        // Borrar los productos del carrito y volver a cargar el adaptador
        clearSharedPreferences("productos_carrito");


        // Detener y ocultar la animación después de un tiempo (por ejemplo, 2 segundos)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activityMyCartBinding.btnBorrarCarrito.cancelAnimation();
                activityMyCartBinding.btnBorrarCarrito.setVisibility(View.INVISIBLE);

                activityMyCartBinding.RecyclerMyCart.setVisibility(View.INVISIBLE);
               //obtenerProductosDelCarrito(); // Volver a cargar el adaptador del carrito

                mCarritoAdapter.setCarrito(obtenerProductosDelCarrito());
            }
        }, 2000);
        activityMyCartBinding.btnBorrarCarrito.setVisibility(View.VISIBLE);
    }
    // Función para realizar la compra
    public void Shopping(View view) {
        Toast.makeText(context, "Enviado", Toast.LENGTH_SHORT).show();
    }

    // Función para solicitar permisos de ubicación
    public void getPermission() {
        // Verificar si ya se han otorgado los permisos de ubicación
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

    // Función para obtener la ubicación del usuario
    public void obtenerUbicacion() {
        // Crear una instancia de LocationManager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Verificar si el GPS y la red están habilitados
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            // Obtener la última ubicación conocida del usuario
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                // Si se encuentra una ubicación, mostrarla
                if (lastKnownLocation != null) {
                    double latitud = lastKnownLocation.getLatitude();
                    double longitud = lastKnownLocation.getLongitude();
                    saveLocationData(latitud, longitud);
                } else {
                    // Si no se encuentra una ubicación, solicitar una actualización de ubicación
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        } else {
            // Si el GPS y la red no están habilitados, solicitar al usuario que los active
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
    }

    // Función para guardar los datos de ubicación en SharedPreferences
    private void saveLocationData(double latitud, double longitud) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitud, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (!addresses.isEmpty()) {
            String ciudad = addresses.get(0).getLocality();
            String departamento = addresses.get(0).getAdminArea();

            // Obtener la instancia del SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MiUbicacion", Context.MODE_PRIVATE);

            // Editar el SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("distrito", ciudad);
            editor.putString("departamento", departamento);
            editor.apply();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_PERMISOS_UBICACION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de ubicación otorgado
                obtenerUbicacion();
            } else {
                Toast.makeText(context, "Es necesario su ubicación para poder enviarle el producto", Toast.LENGTH_SHORT).show();
                // Permiso de ubicación denegado
                // Realizar acciones adicionales o mostrar un mensaje al usuario
            }
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitud = location.getLatitude();
            double longitud = location.getLongitude();

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.removeUpdates(locationListener);

            saveLocationData(latitud, longitud);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    @Override
    protected void onResume() {
        getLocation();
        super.onResume();
    }

    @SuppressLint("SetTextI18n")
    private void getLocation() {
        SharedPreferences sharedPreferences = getSharedPreferences("MiUbicacion", Context.MODE_PRIVATE);
        String departamentoGuardada = sharedPreferences.getString("departamento", "");
        String distritoGuardado = sharedPreferences.getString("distrito", "");

        if (!TextUtils.isEmpty(departamentoGuardada) && !TextUtils.isEmpty(distritoGuardado)) {
            if (departamentoGuardada.contains("Provincia de ")) {
                departamentoGuardada = departamentoGuardada.replaceAll("Provincia de ", "");
            }

            activityMyCartBinding.Departamento.setText(departamentoGuardada + ",");
            activityMyCartBinding.Distrito.setText(distritoGuardado);
            //Log.d("Departamento: ", departamentoGuardada);
            //Log.d("Distrito: ", distritoGuardado);
            //Toast.makeText(context, "Departamento" + departamentoGuardada, Toast.LENGTH_LONG).show();
            //Toast.makeText(context, "Distrito" + distritoGuardado, Toast.LENGTH_LONG).show();
        } else {
            // Los valores están vacíos, puedes mostrar un mensaje o realizar otras acciones
        }
    }

    public void clearSharedPreferences(String s) {
        SharedPreferences preferences = context.getSharedPreferences(s, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public List<ProductosItem> obtenerProductosDelCarrito() {
        // Obtén la lista de productos desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("productos_carrito", MODE_PRIVATE);
        String listaProductosJson = sharedPreferences.getString("lista_productos_carrito", "");

        // Convierte el JSON a una lista de ProductosItem utilizando Gson
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductosItem>>() {}.getType();
        List<ProductosItem> listaProductosItems = gson.fromJson(listaProductosJson, type);
        mostrarCardsCarrito(listaProductosItems);
        mCarritoAdapter.setCarrito(listaProductosItems);
        //Toast.makeText(context, ""+listaProductosItems, Toast.LENGTH_SHORT).show();

        calcularSubTotal();
        calcularTotal();
        return listaProductosItems;
    }

    public void mostrarCardsCarrito(List<ProductosItem> carrito) {
        // Mostrar los datos del carrito
        carritoList = carrito;
        if (carrito != null && !carrito.isEmpty()) {
            mCarritoAdapter.setCarrito(carrito);
            activityMyCartBinding.RecyclerMyCart.setAdapter(mCarritoAdapter);
            activityMyCartBinding.RecyclerMyCart.setLayoutManager(new LinearLayoutManager(MyCartActivity.this, RecyclerView.VERTICAL, false));

            activityMyCartBinding.viewSwitcher.setDisplayedChild(1);
            activityMyCartBinding.animateView.setVisibility(View.GONE);
            activityMyCartBinding.btnCheckout.setEnabled(true);
        } else {
            activityMyCartBinding.animateView.setVisibility(View.VISIBLE);
            activityMyCartBinding.viewSwitcher.setDisplayedChild(0);
            activityMyCartBinding.btnCheckout.setEnabled(false);
        }
    }

    @SuppressLint("SetTextI18n")
    public void calcularSubTotal() {
        if (carritoList != null && !carritoList.isEmpty()) {
            double subTotal = 0;
            for (ProductosItem item : carritoList) {
                subTotal += item.getPrecioUnitario();
            }
            activityMyCartBinding.textSubTotal.setText("S/ "+ String.format(Locale.getDefault(), "%.2f", subTotal));
        } else {
            // La lista está vacía o nula, realiza alguna acción o muestra un mensaje adecuado
            activityMyCartBinding.textSubTotal.setText("S/ 0.00");
        }
    }
    @SuppressLint("SetTextI18n")
    public void calcularTotal() {
        if (carritoList != null && !carritoList.isEmpty()) {
            double subTotal = 0;
            for (ProductosItem item : carritoList) {
                double precio = item.getPrecioUnitario() * item.getAmount();
                subTotal += precio;
            }

            // Calcula el descuento y el monto de entrega
            double descuento = 10.0; // Ejemplo: 10% de descuento
            double delivery = 5.0; // Ejemplo: S/. 5 de entrega

            // Aplica el descuento al subtotal
            double descuentoAmount = subTotal * (descuento / 100);
            double subtotalConDescuento = subTotal - descuentoAmount;

            // Calcula el total sumando el subtotal con descuento y el monto de entrega
            double total = subtotalConDescuento + delivery;

            BigDecimal bd = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP);
            double roundedPrice = bd.doubleValue();
            activityMyCartBinding.textTotal.setText("S/ " + String.format(Locale.getDefault(), "%.2f", roundedPrice));
            //Log.d("cantidad: ", carritoList.toString());
            //Toast.makeText(context, "==> "+ carritoList.toString(), Toast.LENGTH_SHORT).show();
        } else {
            // La lista está vacía o nula, realiza alguna acción o muestra un mensaje adecuado
            activityMyCartBinding.textTotal.setText("S/ 0.00");
        }
    }

    public void volver(View view){
        NavigationContent.cambiarActividad(this, MainActivity.class);
    }


}