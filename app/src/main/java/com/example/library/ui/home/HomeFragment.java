package com.example.library.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.Contribution;
import com.example.library.ModelEx;
import com.example.library.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter fireBaseRecyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        recyclerView = root.findViewById(R.id.list);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();
        return root;
    }
    private void fetch() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Books");
        FirebaseRecyclerOptions<ModelEx> options = new FirebaseRecyclerOptions.Builder<ModelEx>().setQuery(query, new SnapshotParser<ModelEx>() {
            @NonNull
            @Override
            public ModelEx parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new ModelEx(snapshot.child("name").getValue().toString(),snapshot.child("author").getValue().toString(),snapshot.child("category").getValue().toString(),
                        snapshot.child("downloadImage").getValue().toString());

            }
        }).build();
        fireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelEx, ViewHolder>(options) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelEx model) {
                holder.setTxtTitle(model.getmTitle());
                holder.setTxtDesc(model.getmDesc());
                holder.setImageView(model.getmImageURL());
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeFragment.this.getActivity(),"asd",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerView.setAdapter(fireBaseRecyclerAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        fireBaseRecyclerAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        fireBaseRecyclerAdapter.stopListening();
    }


}