package com.tinieblas.tokomegawa.domain.repository;

import com.tinieblas.tokomegawa.domain.repository.base.UserRepositoryBase;

public interface UserLocalRepository extends UserRepositoryBase {
    @Override
    String getUser();

    @Override
    String getCurrentUserId();
}
