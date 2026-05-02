package com.example.pccontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private WinAppFragment winAppFragment = new WinAppFragment();

    private ConsoleFragment consoleFragment = new ConsoleFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        View bottomNav = findViewById(R.id.bottomNavigationView2);

        setNewFragment(winAppFragment);

        ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (v, insets) -> {
            v.setPadding(0, 0, 0, 0);
            return insets;
        });

        frameLayout = findViewById(R.id.fameMain);
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);


        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.homeBut) {
                setNewFragment(winAppFragment);
                return true;
            }
            if (item.getItemId() == R.id.consoleBut) {
                setNewFragment(consoleFragment);
                return true;

            }
            if (item.getItemId() == R.id.settingsBut) {
                setNewFragment(settingsFragment);
                return true;
            }


            return false;
        });


    }


    public void switchFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
             R.anim.fade_in,
                R.anim.fade_out

        );

        ft.replace(R.id.fameMain, fragment);
        ft.addToBackStack(tag);
        ft.commit();
    }
    public  void setNewFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(
                R.anim.fade_in,   // enter
                R.anim.fade_out   // exit

        );
        ft.replace(R.id.fameMain, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }
}