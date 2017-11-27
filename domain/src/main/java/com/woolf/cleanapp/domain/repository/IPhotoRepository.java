package com.woolf.cleanapp.domain.repository;


import com.woolf.cleanapp.domain.model.PhotoDomainModel;

import java.util.Map;

import io.reactivex.Single;

public interface IPhotoRepository {

    Single<Boolean> isFavorite(String id);

    Single<Boolean> addToFavorite(PhotoDomainModel photo);

    Single<Boolean> removeFromFavorite(String id);

    Single<PhotoDomainModel> getPhotoById(String id, Map<String, String> params);
}
