package com.example.uas_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.uas_mobile.MainActivity;
import com.example.uas_mobile.R;
import com.example.uas_mobile.databinding.ActivitySplashBinding;
import com.example.uas_mobile.repository.AppRepository;
import com.example.uas_mobile.viewmodel.AppViewModel;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tambahkan animasi fade in dan zoom pada gambar splash
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_zoom);
        binding.imgLoading.startAnimation(animation);

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        viewModel.getAllEndemik().observe(this, list -> {
            // Cek apakah data sudah ada dan valid (kategori tidak null)
            boolean dataValid = list != null && !list.isEmpty() && list.get(0).getKategori() != null;
            
            if (!dataValid) {
                fetchData();
            } else {
                // Beri sedikit delay agar splash terlihat
                binding.getRoot().postDelayed(() -> {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }, 2000);
            }
        });
    }

    private void fetchData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.tvStatus.setText("Memuat data...");
        viewModel.fetchData(new AppRepository.RepositoryCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                });
            }
            @Override
            public void onError() {
                runOnUiThread(() -> binding.tvStatus.setText("Gagal mengunduh data"));
            }
        });
    }
}
