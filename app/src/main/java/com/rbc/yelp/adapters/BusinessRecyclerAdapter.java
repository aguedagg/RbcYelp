package com.rbc.yelp.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rbc.yelp.R;
import com.rbc.yelp.models.Business;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusinessRecyclerAdapter extends RecyclerView.Adapter<BusinessRecyclerAdapter.BusinessViewHolder> {

    private List<Business> data;

    public BusinessRecyclerAdapter(List<Business> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {

        Business business = (Business) data.get(position);

        holder.titleTextView.setText(business.getName());

        Picasso.get()
                .load(business.getThumbnail().trim().isEmpty() ? null : business.getThumbnail())
                .placeholder(R.drawable.placeholder)
                .resize(80, 80)
                .into(holder.thumbnailImageView);

        holder.categoryTextView.setText(TextUtils.join(", ", business.getCategories()));
        holder.reviewsTextView.setText("(" + business.getNumReviews() + ")");
        holder.ratingBarView.setRating(Float.parseFloat(business.getRating()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView categoryTextView;
        private TextView reviewsTextView;
        private ImageView thumbnailImageView;
        private RatingBar ratingBarView;

        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.tvName);
            categoryTextView = itemView.findViewById(R.id.tvCategory);
            thumbnailImageView = itemView.findViewById(R.id.imageView);
            reviewsTextView = itemView.findViewById(R.id.tvNumReviews);
            ratingBarView = itemView.findViewById(R.id.ratingBar);
        }
    }
}
