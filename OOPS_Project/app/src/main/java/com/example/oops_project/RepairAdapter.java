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

public class RepairAdapter extends ListAdapter<Repair, RepairAdapter.RepairHolder> {

    private onItemClickListener listener;

    public RepairAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Repair> DIFF_CALLBACK = new DiffUtil.ItemCallback<Repair>() {
        @Override
        public boolean areItemsTheSame(@NonNull Repair oldItem, @NonNull Repair newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Repair oldItem, @NonNull Repair newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getPriority().equals(newItem.getPriority());
        }
    };

    @NonNull
    @Override
    public RepairHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item4, parent, false);
        return new RepairHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepairHolder holder, int position) {
        Repair currentNode = getItem(position);
        holder.textViewTitle.setText(currentNode.getTitle());
        holder.textViewDescription.setText(currentNode.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNode.getPriority()));
        holder.textViewDateTime.setText(currentNode.getDate());

        if (currentNode.getPriority().equals("Appliance Repair")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#4CAF50"));
        } else if (currentNode.getPriority().equals("Plumber")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#3299d9"));
        } else if (currentNode.getPriority().equals("Electrician")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFC107"));
        } else if (currentNode.getPriority().equals("Carpenter")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FF5722"));
        }
    }

    public Repair getRepairAt(int position) {
        return getItem(position);
    }

    class RepairHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewDateTime;
        private MaterialCardView cardView;

        public RepairHolder(@NonNull final View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title4);
            textViewDescription = itemView.findViewById(R.id.text_view_description4);
            textViewPriority = itemView.findViewById(R.id.text_view_priority4);
            cardView = itemView.findViewById(R.id.cardView4);
            textViewDateTime = itemView.findViewById(R.id.text_view_date_time4);

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
        void onItemClick(Repair note);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}