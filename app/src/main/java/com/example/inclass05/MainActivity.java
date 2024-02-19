package com.example.inclass05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
        AppCategoriesFragment.AppCategoriesFragmentInterface, AppsListFragment.AppsListFragmentInterface {

    public static final String TAG = "Debug";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        // Launch AppCategoriesFragment
        fragmentManager.beginTransaction()
                .add(R.id.rootView, new AppCategoriesFragment())
                .commit();

    }

    // Override method from AppCategoriesFragmentInterface - Receive category string sent, launch AppsListFragment.
    @Override
    public void sendSelectedCategory(String category) {
        fragmentManager.beginTransaction()
                .replace(R.id.rootView, AppsListFragment.newInstance(category))
                .addToBackStack(null)
                .commit();
    }

    // Override method from AppsListFragmentInterface - Receive array list of app details, launch AppDetailsFragment.
    @Override
    public void sendSelectedApp(DataServices.App app) {
        fragmentManager.beginTransaction()
                .replace(R.id.rootView, AppDetailsFragment.newInstance(app))
                .addToBackStack(null)
                .commit();
    }
}