package com.example.library.ui.home;

import android.icu.util.ValueIterator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.NavigationActivity;
import com.example.library.R;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public TextView txtTitle;
    public TextView txtDesc;
    public ImageView imageView;

    public ViewHolder(View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.list_root);
        txtTitle = itemView.findViewById(R.id.list_title);
        txtDesc = itemView.findViewById(R.id.list_desc);
        imageView= itemView.findViewById(R.id.list_image);
    }

    public void setTxtTitle(String string) {
        txtTitle.setText(string);
    }

    public void setImageView(String string) {
        Picasso.get().load(string).fit().centerCrop().into(imageView);
    }

    public void setTxtDesc(String string) {
        txtDesc.setText(string);
    }
}
