package com.tbb.taamcollection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportItemFragment extends Fragment {

    // Default constructor
    public ReportItemFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // The layout file should be created in the res/layout directory
        View view = inflater.inflate(R.layout.sample_fragment_view, container, false);

        // TODO: Initialize UI elements here and set up any necessary listeners

        return view;
    }
}