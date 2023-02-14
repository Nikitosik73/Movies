package com.example.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.pojo.review.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String TYPE_POSITIVE = "Позитивный";
    private static final String TYPE_NEGATIVE = "Негативный";
    private static final String TYPE_NEUTRAL = "Нейтральный";

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false
        );
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        Review review = reviews.get(position);

        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewReview.setText(review.getReview());

        String type = review.getType();
        int backgroundId = R.color.red;

        switch (type){
            case TYPE_POSITIVE:
                backgroundId = R.color.green;
                break;
            case TYPE_NEUTRAL:
                backgroundId = R.color.orange;
                break;
        }

        int color = ContextCompat.getColor(holder.itemView.getContext(), backgroundId);
        holder.linearLayoutReview.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewAuthor;
        private TextView textViewReview;
        private LinearLayout linearLayoutReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewReview = itemView.findViewById(R.id.textViewReview);
            linearLayoutReview = itemView.findViewById(R.id.linerLayoutReview);
        }

    }
}
