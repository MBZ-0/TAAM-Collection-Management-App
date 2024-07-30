package com.tbb.taamcollection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<HashMap<Integer, List<Boolean>>> checkBoxState = new MutableLiveData<>();

    public LiveData<HashMap<Integer, List<Boolean>>> getCheckBoxState() {
        return checkBoxState;
    }

    public void setCheckBoxState(HashMap<Integer, List<Boolean>> state) {
        checkBoxState.setValue(state);
    }
}
