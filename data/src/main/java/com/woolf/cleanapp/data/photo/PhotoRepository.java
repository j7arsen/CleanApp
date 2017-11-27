package com.woolf.cleanapp.data.photo;


import com.woolf.cleanapp.data.IApiService;
import com.woolf.cleanapp.data.cache.ICache;
import com.woolf.cleanapp.data.photo.mapper.IPhotoModelMapper;
import com.woolf.cleanapp.domain.model.PhotoDomainModel;
import com.woolf.cleanapp.domain.repository.IPhotoRepository;

import java.util.Map;

import io.reactivex.Single;

public class PhotoRepository implements IPhotoRepository {

    private IApiService apiService;
    private ICache cache;
    private IPhotoModelMapper mapper;

    public PhotoRepository(IApiService apiService, ICache cache, IPhotoModelMapper mapper) {
        this.apiService = apiService;
        this.cache = cache;
        this.mapper = mapper;
    }

    @Override
    public Single<Boolean> isFavorite(String id) {
        return cache.isCached(id);
    }

    @Override
    public Single<Boolean> addToFavorite(PhotoDomainModel photo) {
        return Single.fromCallable(() -> mapper.mapDomainToCache(photo)).flatMap(model -> cache.addToFavorites(model));
    }

    @Override
    public Single<Boolean> removeFromFavorite(String id) {
        return cache.removeFromFavorites(id);
    }

    @Override
    public Single<PhotoDomainModel> getPhotoById(String id, Map<String, String> params) {
        return cache.isCached(id).flatMap(isCached -> {
            if (!isCached) {
                return apiService.getPhotoById(id, params).map(domainModel -> mapper.mapEntityToDomain(domainModel));
            } else {
                return cache.getPhotoById(id).map(model1 -> mapper.mapCacheToDomain(model1));
            }
        });
    }
}
