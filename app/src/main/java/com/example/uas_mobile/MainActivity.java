package com.example.uas_mobile;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.uas_mobile.databinding.ActivityMainBinding;
import com.example.uas_mobile.ui.FavoritActivity;
import com.example.uas_mobile.ui.HewanFragment;
import com.example.uas_mobile.ui.ProfileFragment;
import com.example.uas_mobile.ui.TumbuhanFragment;
import com.example.uas_mobile.utils.ThemeStorage;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ThemeStorage themeStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        themeStorage = new ThemeStorage(this);
        // Terapkan tema yang tersimpan sebelum setContentView
        if (themeStorage.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.inflateMenu(R.menu.toolbar_menu);
        
        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) return new HewanFragment();
                if (position == 1) return new TumbuhanFragment();
                return new ProfileFragment();
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        binding.viewPager.setUserInputEnabled(false);

        binding.bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_hewan) {
                binding.viewPager.setCurrentItem(0, false);
            } else if (item.getItemId() == R.id.menu_tumbuhan) {
                binding.viewPager.setCurrentItem(1, false);
            } else {
                binding.viewPager.setCurrentItem(2, false);
            }
            return true;
        });

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_favorite) {
                startActivity(new Intent(this, FavoritActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_theme) {
                if (themeStorage.isDarkMode()) {
                    themeStorage.setDarkMode(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    themeStorage.setDarkMode(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                return true;
            }
            return false;
        });
    }
}
