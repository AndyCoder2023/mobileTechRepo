package com.example.mobiletech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mobiletech.data.HourForecast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Create some sample data
        HourForecast hf = new HourForecast();
        hf.setTemperature(32);
    }

    //    TODO: Menu bar not appearing
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle settings item selection
        if  (item.getItemId() == R.id.mi_appBarHome) {
            NavController navController = Navigation.findNavController(findViewById(R.id.fragmentContainerView));
            // work out where the user currently is
            int currentFragmentId = navController.getCurrentDestination().getId();
            // if that is different from the settings fragment
            if (currentFragmentId != R.id.selectLocationFragment) {
                navController.navigate(R.id.selectLocationFragment);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

        //    Bottom Navigation
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            NavController navController = Navigation.findNavController(findViewById(R.id.fragmentContainerView));
//            // work out where the user currently is
//            int currentFragmentId = navController.getCurrentDestination().getId();
//
//            if (item.getItemId() == R.id.mi_bottomNavSettings) {
//                // navigate "home" to the location select fragment
//                if (currentFragmentId != R.id.selectLocationFragment) {
//                    navController.navigate(R.id.selectLocationFragment);
//                    return true;
//                }
//            } else if (item.getItemId() == R.id.mi_bottomNavSettings){
//                // navigate to settings fragment
//                if (currentFragmentId != R.id.settingsFragment){
//                    navController.navigate(R.id.settingsFragment);
//                    return true;
//                }
//
//            }
//            return false;
//        }
    }