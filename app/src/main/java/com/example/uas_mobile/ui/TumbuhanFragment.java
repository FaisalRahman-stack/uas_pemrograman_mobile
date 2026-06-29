package com.example.uas_mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.uas_mobile.R;
import com.example.uas_mobile.adapter.EndemikAdapter;
import com.example.uas_mobile.databinding.FragmentListBinding;
import com.example.uas_mobile.viewmodel.AppViewModel;
import com.google.android.material.chip.Chip;

public class TumbuhanFragment extends Fragment {
    private FragmentListBinding binding;
    private EndemikAdapter adapter;
    private AppViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        adapter = new EndemikAdapter();
        binding.rvList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvList.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        observeData("Semua");

        binding.chipGroupFilter.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip != null) {
                observeData(chip.getText().toString());
            }
        });

        adapter.setOnItemClick(item -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("ID", item.getId());
            startActivity(intent);
        });
        return binding.getRoot();
    }

    private void observeData(String filter) {
        if (filter.equals("Semua")) {
            viewModel.getEndemikByKategori("Tumbuhan").observe(getViewLifecycleOwner(), list -> adapter.setData(list));
        } else {
            viewModel.getEndemikByKategoriAndAsal("Tumbuhan", filter).observe(getViewLifecycleOwner(), list -> adapter.setData(list));
        }
    }
}
