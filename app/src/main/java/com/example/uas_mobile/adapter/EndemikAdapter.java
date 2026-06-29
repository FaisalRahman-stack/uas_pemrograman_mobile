package com.example.uas_mobile.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.uas_mobile.R;
import com.example.uas_mobile.databinding.ItemGridBinding;
import com.example.uas_mobile.model.Endemik;
import java.util.ArrayList;
import java.util.List;

public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.ViewHolder> {
    private List<Endemik> list = new ArrayList<>();
    private OnItemClick callback;

    public void setData(List<Endemik> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick callback) { this.callback = callback; }

    @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemGridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Endemik item = list.get(position);
        holder.binding.tvTitle.setText(item.getNama());
        
        int placeholder = "Tumbuhan".equalsIgnoreCase(item.getKategori()) ? 
                R.drawable.ic_tumbuhan : R.drawable.ic_hewan;

        Glide.with(holder.itemView.getContext())
                .load(item.getGambar())
                .placeholder(placeholder)
                .error(placeholder)
                .into(holder.binding.imgItem);

        holder.itemView.setOnClickListener(v -> {
            if (callback != null) callback.onClick(item);
        });
    }

    @Override public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemGridBinding binding;
        ViewHolder(ItemGridBinding binding) { super(binding.getRoot()); this.binding = binding; }
    }

    public interface OnItemClick { void onClick(Endemik item); }
}
