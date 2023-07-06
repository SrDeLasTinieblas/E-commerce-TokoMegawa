package com.tinieblas.tokomegawa.domain.repository;

import com.tinieblas.tokomegawa.domain.repository.base.UserRepositoryBase;

public interface UserRemoteRepository extends UserRepositoryBase {

    void uploadUser();

    @Override
    String getUser();

    @Override
    String getCurrentUserId();
}
