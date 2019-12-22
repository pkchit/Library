package com.example.library.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Interface.ItemClickListener;
import com.example.library.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView bookName, bookCategory, bookAuthor, bookDescription;
    public ImageView bookImage;
    public  ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        bookImage = itemView.findViewById(R.id.book_image_display);
        bookName = itemView.findViewById(R.id.book_name_display);
        bookCategory = itemView.findViewById(R.id.book_category_display);
        bookAuthor = itemView.findViewById(R.id.book_author_display);
        bookDescription  =itemView.findViewById(R.id.book_description_display);

    }
    public  void setitemClickListener(ItemClickListener listener)
    {
        this.listener= listener;
    }

    @Override
    public void onClick(View v)
    {
        listener.onClick(v , getAdapterPosition(), false);
    }
}
