package com.example.library.ui.req;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.R;
import com.example.library.ui.gallery.GalleryFragment;
import com.example.library.ui.gallery.RequestListModel;
import com.example.library.ui.home.HomeFragment;
import com.example.library.ui.home.ViewHolder;
import com.example.library.ui.send.SendViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReqFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_req, container, false);
        mAuth= FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        recyclerView = root.findViewById(R.id.list_request);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();
        return root;
    }
    public void fetch() {
        final Bundle bb=getArguments();
        final String user=bb.getString("userID");
        final String book=bb.getString("bookID");
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference ref=db.getReference("requests");
        Query query=ref.child(user).child(book);
        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder<String>().setQuery(query, new SnapshotParser<String>() {
            @NonNull
            @Override
            public String parseSnapshot(@NonNull DataSnapshot snapshot) {
                return snapshot.getKey();
            }
        }).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<String, RequestsViewHolder>(options) {
            @NonNull
            @Override
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.request_list, parent, false);

                return new RequestsViewHolder(view);
            }
            protected void onBindViewHolder(@NonNull final RequestsViewHolder holder, int position, @NonNull final String model) {
                holder.setTxtTitle(model);
                holder.chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle b=new Bundle();
                        b.putString("requesteeID",user);
                        b.putString("requesterID",model);
                        Toast.makeText(ReqFragment.this.getActivity(),model+" rocks",Toast.LENGTH_SHORT).show();

                        //Navigation code to be written by Puneet to proceed to the chat activity/fragment
                        Navigation.findNavController(ReqFragment.this.getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_chat, b);

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
