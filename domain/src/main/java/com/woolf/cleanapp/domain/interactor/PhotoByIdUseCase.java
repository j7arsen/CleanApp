package com.woolf.cleanapp.domain.interactor;


import com.woolf.cleanapp.domain.model.PhotoDomainModel;
import com.woolf.cleanapp.domain.repository.IPhotoRepository;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class PhotoByIdUseCase extends BaseUseCase<PhotoDomainModel, PhotoByIdUseCase.Params> {

    private IPhotoRepository photoRepository;

    public PhotoByIdUseCase(IPhotoRepository photoRepository, Scheduler mainThread, Scheduler backgroundThread) {
        super(mainThread, backgroundThread);
        this.photoRepository = photoRepository;
    }

    @Override
    Flowable<PhotoDomainModel> buildUseCaseObservable(PhotoByIdUseCase.Params params) {
        return photoRepository.getPhotoById(params.id, params.options);
    }

    public static class Params {
        private String id;
        private HashMap<String, String> options;

        public Params(String id, HashMap<String, String> options) {
            this.id = id;
            this.options = options;
        }
    }
}