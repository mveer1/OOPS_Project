package com.example.oops_project;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

public class StockAdapter extends ListAdapter<Stock, StockAdapter.StockHolder> {

    private onItemClickListener listener;

    public StockAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Stock> DIFF_CALLBACK = new DiffUtil.ItemCallback<Stock>() {
        @Override
        public boolean areItemsTheSame(@NonNull Stock oldItem, @NonNull Stock newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Stock oldItem, @NonNull Stock newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getPriority().equals(newItem.getPriority());
        }
    };

    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item3, parent, false);
        return new StockHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        Stock currentNode = getItem(position);
        holder.textViewTitle.setText(currentNode.getTitle());
        holder.textViewDescription.setText(currentNode.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNode.getPriority()));
        holder.textViewDateTime.setText(currentNode.getDate());

        if (currentNode.getPriority().equals("High")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FF5722"));
        } else if (currentNode.getPriority().equals("Medium")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFC107"));
        } else if (currentNode.getPriority().equals("Low")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#4CAF50"));
        }

    }

    public Stock getStockAt(int position) {
        return getItem(position);
    }


    class StockHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewDateTime;
        private MaterialCardView cardView;

        public StockHolder(@NonNull final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title3);
            textViewDescription = itemView.findViewById(R.id.text_view_description3);
            textViewPriority = itemView.findViewById(R.id.text_view_priority3);
            cardView = itemView.findViewById(R.id.cardView3);
            textViewDateTime = itemView.findViewById(R.id.text_view_date_time3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Stock stock);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
