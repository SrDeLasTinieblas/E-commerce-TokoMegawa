package com.tinieblas.tokomegawa.domain.repository;

import com.tinieblas.tokomegawa.domain.models.LocationData;

public interface LocationRepository {
    void saveLocation(String city, String department);
    LocationData getLocation();
}
