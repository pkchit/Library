package com.example.library.ui.gallery;

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
import androidx.navigation.Navigation;
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

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private FirebaseDatabase database;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        galleryViewModel =
//                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
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
        database = FirebaseDatabase.getInstance();
        FirebaseAuth  mAuth = FirebaseAuth.getInstance();
        FirebaseUser user  = mAuth.getCurrentUser();
        String ss=user.getEmail().replace(".","=*=");
        Query query=database.getReference("requests").child(ss);
        //Query query = FirebaseDatabase.getInstance().getReference().child("Books");
        FirebaseRecyclerOptions<RequestListModel> options = new FirebaseRecyclerOptions.Builder<RequestListModel>().setQuery(query, new SnapshotParser<RequestListModel>() {
            @NonNull
            @Override
            public RequestListModel parseSnapshot(@NonNull DataSnapshot snapshot) {
//                return new ModelEx(snapshot.getKey(),snapshot.child("name").getValue().toString(),snapshot.child("owner").getValue().toString(),snapshot.child("author").getValue().toString(),snapshot.child("downloadImage").getValue().toString());
                //return new ModelEx(snapshot.child("name").getValue().toString(),snapshot.child("author").getValue().toString(),snapshot.child("category").getValue().toString(),
                //snapshot.child("downloadImage").getValue().toString());
                return new RequestListModel(snapshot.getKey());
            }
        }).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RequestListModel, ViewHolder>(options) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final RequestListModel model) {
                FirebaseAuth mAuth=FirebaseAuth.getInstance();
                FirebaseUser user=mAuth.getCurrentUser();

                    holder.setTxtTitle(model.getBookID());
                    holder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(GalleryFragment.this.getActivity(), "Proceeding to Requests", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(GalleryFragment.this.getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_req);

                        }
                    });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}
