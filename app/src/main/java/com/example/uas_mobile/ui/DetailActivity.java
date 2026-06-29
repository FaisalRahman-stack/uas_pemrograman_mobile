package com.example.uas_mobile.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.uas_mobile.R;
import com.example.uas_mobile.databinding.ActivityDetailBinding;
import com.example.uas_mobile.model.Endemik;
import com.example.uas_mobile.viewmodel.AppViewModel;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private AppViewModel viewModel;
    private boolean isFavorite = false;
    private Endemik currentEndemik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        String id = getIntent().getStringExtra("ID");
        if (id != null) {
            viewModel.getEndemikById(id).observe(this, endemik -> {
                if (endemik != null) {
                    currentEndemik = endemik;
                    binding.tvTitle.setText(endemik.getNama());
                    binding.tvLatin.setText(endemik.getNamaLatin());
                    binding.tvAsal.setText("Asal: " + endemik.getAsal());
                    binding.tvStatus.setText("Status: " + endemik.getStatus());
                    binding.tvDesc.setText(endemik.getDeskripsi());
                    Glide.with(this).load(endemik.getGambar()).into(binding.imgFull);
                }
            });

            viewModel.getFavoritById(id).observe(this, favorit -> {
                isFavorite = favorit != null;
                binding.btnFav.setImageResource(isFavorite ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
            });
        }

        binding.btnFav.setOnClickListener(v -> {
            if (currentEndemik != null) {
                viewModel.toggleFavorit(currentEndemik, isFavorite);
            }
        });
    }
}
