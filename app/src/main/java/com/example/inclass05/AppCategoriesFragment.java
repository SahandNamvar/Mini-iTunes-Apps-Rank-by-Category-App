package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.inclass05.databinding.FragmentAppCategoriesBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AppCategoriesFragment extends Fragment {

    // Set up binding
    FragmentAppCategoriesBinding binding;
    // Set up adapter
    ArrayAdapter<String> adapter;
    // Set up categories arrayList
    ArrayList<String> categories;

    public AppCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("App Categories");

        categories = DataServices.getAppCategories(); // Return keySet() of apps HashMap - Key values of categories

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categories);

        binding.listView.setAdapter(adapter);

        // onItemClickListener()
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String category = categories.get(position); // Based on the position clicked get the string value
                mListener.sendSelectedCategory(category);
                Log.d(MainActivity.TAG, "AppCategories onItemClick: " + category);
            }
        });
    }


    // Interface setup to communicate selected category back to Main Activity
    AppCategoriesFragmentInterface mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AppCategoriesFragmentInterface) context;
    }

    public interface AppCategoriesFragmentInterface {
        void sendSelectedCategory(String category);
    }
}