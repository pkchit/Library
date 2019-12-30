package com.example.library.ui.reqform;

import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.Contribution;
import com.example.library.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ReqFormFragment extends Fragment {
    private Button button;
    private TextView title;
    private TextView author;
    FirebaseDatabase database;
    DatabaseReference ref;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_reqform,container,false);
        database=FirebaseDatabase.getInstance();
        final Button b=root.findViewById(R.id.reqest_button);
        title=root.findViewById(R.id.titleC);
        author=root.findViewById(R.id.authorC);
        final Bundle bb=getArguments();
        final String s=bb.getString("key");
        //title.setText(s);
        database.getReference().child("dasd").child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Contribution contri=dataSnapshot.getValue(Contribution.class);
                title.setText(contri.getId());
                author.setText(contri.getAuthor());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref=database.getReference();
                final FirebaseAuth mAuth=FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();
                author.setText(user.getEmail());
                System.out.println(user.getEmail());
//                ref.child(user.getEmail()).child(s).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()) {
//                            b.setEnabled(false);
//                        } else {
//                            b.setEnabled(true);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

                HashMap<String,Object> hm=new HashMap<>();
                hm.put(user.getEmail(),true);
                ref.push().child(s).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        HashMap<String,Object> hm2=new HashMap<>();
                        hm2.put(s,true);
                        //database.getReference("activeRequests").child(user.getEmail()).updateChildren(hm2);
                        if (task.isSuccessful())
//
                            Toast.makeText(ReqFormFragment.this.getActivity(),"requested owner",Toast.LENGTH_SHORT).show();

                        else
                            Toast.makeText(ReqFormFragment.this.getActivity(),"fuck off bitch",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReqFormFragment.this.getActivity(),"fuck off bitch",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        return root;
    }
}
