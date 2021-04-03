package com.rbc.yelp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rbc.yelp.R;
import com.rbc.yelp.models.Section;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SectionRecyclerAdapter extends RecyclerView.Adapter<SectionRecyclerAdapter.SectionViewHolder> {

    private List<Section> data = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_item, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        final Section section = data.get(position);
        holder.titleTextView.setText(section.getSectionName() + " (" + section.getBusinesses().size() + ")");

        holder.businessRecyclerView.setHasFixedSize(true);
        holder.businessRecyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.businessRecyclerView.setLayoutManager(linearLayoutManager);

        BusinessRecyclerAdapter adapter = new BusinessRecyclerAdapter(section.getBusinesses());
        holder.businessRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Section> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private RecyclerView businessRecyclerView;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.tvSectionHeader);
            businessRecyclerView = itemView.findViewById(R.id.businesses_recyclerview);
        }
    }
}
