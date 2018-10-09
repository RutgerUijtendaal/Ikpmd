package com.rutger.uijtendaal.ikpmd.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public abstract class MediatorResource<ResultType> {

    private final AppExecutors mMppExecutors;

    private final MediatorLiveData<ResultType> result = new MediatorLiveData<>();

    public MediatorResource(AppExecutors appExecutors) {
        mMppExecutors = appExecutors;

        mMppExecutors.diskIO().execute(() ->
                result.addSource(loadFromDb(), data -> result.setValue(data))
        );

        getFromFirebase();

    }

    private void getFromFirebase() {
        FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(getFbRef());
        result.addSource(liveData, dataSnapshot -> {
            mMppExecutors.diskIO().execute(() -> {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    saveFbResult(child);
                }
            });
        });

    }

    public LiveData<ResultType> asLiveData() {
        return result;
    }

    @NonNull
    @WorkerThread
    protected abstract void saveFbResult(DataSnapshot dataSnapshot);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract DatabaseReference getFbRef();
}
