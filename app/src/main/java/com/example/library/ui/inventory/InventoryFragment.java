package com.example.library.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.ModelEx;
import com.example.library.R;
import com.example.library.ui.home.HomeFragment;
import com.example.library.ui.home.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class InventoryFragment extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private  FirebaseRecyclerAdapter fireBaseRecyclerAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
   

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);


        mAuth= FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        recyclerView = rootView.findViewById(R.id.list_inventory);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();
        return rootView;
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance().getReference("Books");
        FirebaseRecyclerOptions<ModelEx> options = new FirebaseRecyclerOptions.Builder<ModelEx>().setQuery(query, new SnapshotParser<ModelEx>() {
            @NonNull
            @Override
            public ModelEx parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new ModelEx(snapshot.getKey(),snapshot.child("name").getValue().toString(),snapshot.child("owner").getValue().toString(),snapshot.child("author").getValue().toString(),
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
                holder.setTxtDesc(model.getmauthor());
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InventoryFragment.this.getActivity(),"Hello!!!",Toast.LENGTH_SHORT).show();
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
