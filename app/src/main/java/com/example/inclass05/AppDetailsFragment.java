package com.example.inclass05;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.inclass05.databinding.FragmentAppCategoriesBinding;
import com.example.inclass05.databinding.FragmentAppDetailsBinding;

import java.util.ArrayList;

public class AppDetailsFragment extends Fragment {

    FragmentAppDetailsBinding binding;

    ArrayList<String> genres;
    ArrayAdapter<String> adapter;

    private static final String ARG_PARAM_APP = "ARG_PARAM_APP";
    private DataServices.App mApp;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    // to Create a newInstance of AppDetailsFragment and send it an App
    public static AppDetailsFragment newInstance(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_APP, app);
        fragment.setArguments(args);
        return fragment;
    }


    // Receive App Array List
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mApp = (DataServices.App) getArguments().getSerializable(ARG_PARAM_APP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("App Details");

        binding.textViewAppName.setText(mApp.getName());
        binding.textViewArtistName.setText(mApp.getArtistName());
        binding.textViewReleaseDate.setText(mApp.getReleaseDate());

        genres = mApp.getGenres();
        Log.d(MainActivity.TAG, mApp.getName() + " genres: " + genres);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, genres);

        binding.listView.setAdapter(adapter);
    }
}