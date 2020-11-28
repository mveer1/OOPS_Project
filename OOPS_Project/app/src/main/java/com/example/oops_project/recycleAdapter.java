package com.example.oops_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.myviewholder>{

    String data1[],data2[];
    int image[];
    Context context;

    public recycleAdapter(Context ct, String s1[], String s2[], int img[]){
        context=ct;
        data1=s1;
        data2=s2;
        image = img;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.textView9.setText(data1[position]);
        holder.textView10.setText(data2[position]);
        holder.imageView3.setImageResource(image[position]);

    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        TextView textView9,textView10;
        ImageView imageView3;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            textView9=itemView.findViewById(R.id.textView9);
            textView10=itemView.findViewById(R.id.textView10);
            imageView3 = itemView.findViewById(R.id.imageView5);
        }
    }

}
