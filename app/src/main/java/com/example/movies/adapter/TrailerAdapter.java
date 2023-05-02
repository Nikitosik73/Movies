package com.example.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.pojo.trailer.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailers = new ArrayList<>();

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    private onClickTrailerListener onClickTrailerListener;

    public void setOnClickTrailerListener(TrailerAdapter.onClickTrailerListener onClickTrailerListener) {
        this.onClickTrailerListener = onClickTrailerListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item,
                parent,
                false
        );
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {

        Trailer trailer = trailers.get(position);
        holder.textViewTrailer.setText(trailer.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickTrailerListener != null) {
                    onClickTrailerListener.onClickTrailer(trailer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public interface onClickTrailerListener {

        void onClickTrailer(Trailer trailer);
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewTrailer;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrailer = itemView.findViewById(R.id.textViewTrailer);
        }
    }
}
