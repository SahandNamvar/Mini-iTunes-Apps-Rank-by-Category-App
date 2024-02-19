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
import android.widget.TextView;

import com.example.inclass05.databinding.FragmentAppCategoriesBinding;

import java.util.ArrayList;
import java.util.List;

public class AppsListFragment extends Fragment {

    private static final String ARG_PARA_CATEGORY = "ARG_PARA_CATEGORY";
    private String mCategory;

    // Set up binding
    FragmentAppCategoriesBinding binding;

    // Set up array list of type App to store the apps (values) of the category String (Key) passed to the newInstance from Main Activity & AppsCategoriesFragment
    private ArrayList<DataServices.App> appArrayList;

    // Initialize custom adapter - To render each item (app) inside appArrayList inside a listView.
    AppsAdapter adapter;

    public AppsListFragment() {
        // Required empty public constructor
    }

    // newInstance -- used from Main Activity to launch a new instance of AppsListFragment and send it String category
    public static AppsListFragment newInstance(String category) {
        AppsListFragment fragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARA_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    // When new instance of this fragment is created, receive and assign the category string sent
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_PARA_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAppCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(mCategory);

        binding.textViewWelcome.setText(""); // Not sure if a Bug - but Welcome text stays on, eventhough its attached to AppCategoriesFragment, not Main Activity!

        appArrayList = DataServices.getAppsByCategory(mCategory); // Populate the appArrayList. Get apps (values) of the given key (mCategory)

        adapter = new AppsAdapter(getActivity(), appArrayList); // Render all apps within the appArrayList (which are the keys of the given mCategory key from AppCategoriesFragment)
        binding.listView.setAdapter(adapter); // Set the adapter

        // Set up onItemClick() handler to send the App Name to Main Activity and launch AppDetailsFragment
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DataServices.App appDetails = appArrayList.get(position); // Get app details based on the position of the app in the listView item clicked
                Log.d(MainActivity.TAG, "AppList onItemClick: " + appDetails.getName() + " --> " + appDetails);
                mListener.sendSelectedApp(appDetails);
            }
        });

    }


    // Set up Adapter - Returns a view for each object in a collection of data objects you provide
    class AppsAdapter extends ArrayAdapter<DataServices.App> {
        public AppsAdapter(@NonNull Context context, @NonNull List<DataServices.App> objects) {
            super(context, R.layout.app_list_item, objects);
        }

        // Implement getView
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.app_list_item, parent, false);
            }

            TextView textViewAppName = convertView.findViewById(R.id.textViewAppNameList);
            TextView textViewArtistName = convertView.findViewById(R.id.textViewArtistNameList);
            TextView textViewReleaseDate = convertView.findViewById(R.id.textViewReleaseDateList);

            DataServices.App app = getItem(position);

            textViewAppName.setText(app.getName());
            textViewArtistName.setText(app.getArtistName());
            textViewReleaseDate.setText(app.getReleaseDate());

            return convertView;
        }
    }

    // Set up Interface for sending the selected App from the appArrayList<> to Main Activity to launch AppDetailsFragment.
    AppsListFragmentInterface mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AppsListFragmentInterface) context;
    }

    public interface AppsListFragmentInterface {
        void sendSelectedApp(DataServices.App app);
    }
}