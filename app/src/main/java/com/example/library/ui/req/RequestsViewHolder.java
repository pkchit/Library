package com.example.library.ui.req;

import android.icu.util.ValueIterator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.NavigationActivity;
import com.example.library.R;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

public class RequestsViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public TextView txtTitle;
    public Button chat;
    public RequestsViewHolder(View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.list_root);
        txtTitle = itemView.findViewById(R.id.requester_name);
        chat=itemView.findViewById(R.id.chat_button);
    }

    public void setTxtTitle(String string) {
        txtTitle.setText(string);
    }

}
