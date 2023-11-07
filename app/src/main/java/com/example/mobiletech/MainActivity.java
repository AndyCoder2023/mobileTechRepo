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

public class MainActivity extends AppCompatActivity {
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

//    todo: fix this code
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        handle settings item selection
        if (item.getItemId() == R.id.mi_appBarSetting) {
            NavController navController = Navigation.findNavController(findViewById(R.id.fragmentContainerView));
//            Work out where the user currently is
            int currentFragmentId = navController.getCurrentDestination().getId();
//            If that is different from the settings fragment
            if (currentFragmentId != R.id.settingsFragment) {
                navController.navigate(R.id.settingsFragment);
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        }
}