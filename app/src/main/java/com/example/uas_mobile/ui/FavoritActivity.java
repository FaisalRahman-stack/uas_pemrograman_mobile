package com.example.uas_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.uas_mobile.adapter.EndemikAdapter;
import com.example.uas_mobile.databinding.ActivityFavoritBinding;
import com.example.uas_mobile.model.Endemik;
import com.example.uas_mobile.viewmodel.AppViewModel;
import java.util.ArrayList;
import java.util.List;

public class FavoritActivity extends AppCompatActivity {
    private ActivityFavoritBinding binding;
    private EndemikAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        adapter = new EndemikAdapter();
        binding.rvFavorit.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvFavorit.setAdapter(adapter);

        AppViewModel viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        viewModel.getAllFavorit().observe(this, favoritList -> {
            List<Endemik> endemikList = new ArrayList<>();
            for (com.example.uas_mobile.model.Favorit f : favoritList) {
                endemikList.add(new Endemik(f.getId(), f.getNama(), f.getKategori(), f.getGambar(), f.getDeskripsi()));
            }
            adapter.setData(endemikList);
        });

        adapter.setOnItemClick(item -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("ID", item.getId());
            startActivity(intent);
        });
    }
}
