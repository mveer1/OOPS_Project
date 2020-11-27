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

public class TodoAdapter extends ListAdapter<Todo, TodoAdapter.TodoHolder> {

    private onItemClickListener listener;

    public TodoAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Todo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getPriority().equals(newItem.getPriority());
        }
    };

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item3, parent, false);
        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder3, int position) {
        Todo currentNode = getItem(position);
        holder3.textViewTitle.setText(currentNode.getTitle());
        holder3.textViewDescription.setText(currentNode.getDescription());
        holder3.textViewPriority.setText(String.valueOf(currentNode.getPriority()));
        holder3.textViewDateTime.setText(currentNode.getDate());

        if (currentNode.getPriority().equals("High")) {
            holder3.cardView.setCardBackgroundColor(Color.parseColor("#FF5722"));
        } else if (currentNode.getPriority().equals("Medium")) {
            holder3.cardView.setCardBackgroundColor(Color.parseColor("#FFC107"));
        } else if (currentNode.getPriority().equals("Low")) {
            holder3.cardView.setCardBackgroundColor(Color.parseColor("#4CAF50"));
        }

    }

    public Todo getTodosAt(int position) {
        return getItem(position);
    }


    class TodoHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewDateTime;
        private MaterialCardView cardView;

        public TodoHolder(@NonNull final View itemView3) {
            super(itemView3);
            textViewTitle = itemView3.findViewById(R.id.text_view_title3);
            textViewDescription = itemView3.findViewById(R.id.text_view_description4);
            textViewPriority = itemView3.findViewById(R.id.text_view_priority3);
            cardView = itemView3.findViewById(R.id.cardView3);
            textViewDateTime = itemView3.findViewById(R.id.text_view_date_time3);

            itemView3.setOnClickListener(new View.OnClickListener() {
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
        void onItemClick(Todo stock);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
