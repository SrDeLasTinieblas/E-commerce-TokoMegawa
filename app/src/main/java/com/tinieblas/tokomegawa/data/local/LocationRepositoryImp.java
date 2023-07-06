package com.tinieblas.tokomegawa.data.local;

import android.content.Context;

import com.tinieblas.tokomegawa.data.local.base.SharedRepo;
import com.tinieblas.tokomegawa.domain.models.LocationData;
import com.tinieblas.tokomegawa.domain.repository.LocationRepository;

public class LocationRepositoryImp extends SharedRepo implements LocationRepository {


    private final static String NAME = "MiUbicacion";

    private String KEY_DISTRICT = "distrito";
    private String KEY_DEPARTMENT = "departamento";

    public LocationRepositoryImp(Context context) {
        super(context, NAME);
    }

    @Override
    public void saveLocation(String city, String department) {
        putString(KEY_DISTRICT, city);
        putString(KEY_DEPARTMENT, department);
    }

    @Override
    public LocationData getLocation() {
        String departamentoGuardada = getString(KEY_DEPARTMENT);
        String distritoGuardado = getString(KEY_DISTRICT);
        return new LocationData(distritoGuardado, departamentoGuardada);
    }


}
