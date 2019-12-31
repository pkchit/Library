package com.example.library.ui.reqform;

import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;
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
    private Contribution contri;
    private void setContribution(Contribution contri) {
        this.contri=contri;
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_reqform,container,false);
        database=FirebaseDatabase.getInstance();
        final Button b=root.findViewById(R.id.reqest_button);
        title=root.findViewById(R.id.titleC);
        author=root.findViewById(R.id.authorC);
        final Bundle bb=getArguments();
        final String s=bb.getString("key");
        //title.setText(s);
        database.getReference().child("Books").child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Contribution cont=dataSnapshot.getValue(Contribution.class);
                ReqFormFragment.this.setContribution(cont);
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

                ref=database.getReference("requests");
                final FirebaseAuth mAuth=FirebaseAuth.getInstance();
                final FirebaseUser user = mAuth.getCurrentUser();
                author.setText(user.getEmail());
                System.out.println(user.getEmail());
                final String s1=user.getEmail().replace(".","=*=");
                ref.child(contri.getOwner()).child(s).child(s1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                           Toast.makeText(ReqFormFragment.this.getActivity(),"Already requested",Toast.LENGTH_SHORT).show();
                        } else {
                            //final String s1=user.getEmail().replace(".","=*=");
                            Boolean status=true;
                            final String ss=contri.getOwner();
                            ref.child(ss).child(s).child(s1).setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                        database.getReference("activeRequests").child(s1).child(s).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()) {
//                                    Toast.makeText(ReqFormFragment.this.getActivity(),"Oh yeah",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                System.out.println(e.toString());
//                            }
//                        });
                                    if (task.isSuccessful())
//
                                        Toast.makeText(ReqFormFragment.this.getActivity(),"requested owner",Toast.LENGTH_SHORT).show();

                                    else
                                        Toast.makeText(ReqFormFragment.this.getActivity(),"fuck off bitch",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ReqFormFragment.this.getActivity(),"fuck off",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//
//                HashMap<String,Object> hm=new HashMap<>();
//                hm.put(user.getEmail(),true);
//                final String s1=user.getEmail().replace(".","=*=");
//                Boolean status=true;
//                final String ss=contri.getOwner();
//                ref.child(ss).child(s).child(s1).setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
////                        database.getReference("activeRequests").child(s1).child(s).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
////                            @Override
////                            public void onComplete(@NonNull Task<Void> task) {
////                                if(task.isSuccessful()) {
////                                    Toast.makeText(ReqFormFragment.this.getActivity(),"Oh yeah",Toast.LENGTH_SHORT).show();
////                                }
////                            }
////                        }).addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                System.out.println(e.toString());
////                            }
////                        });
//                        if (task.isSuccessful())
////
//                            Toast.makeText(ReqFormFragment.this.getActivity(),"requested owner",Toast.LENGTH_SHORT).show();
//
//                        else
//                            Toast.makeText(ReqFormFragment.this.getActivity(),"fuck off bitch",Toast.LENGTH_SHORT).show();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        //Toast.makeText(ReqFormFragment.this.getActivity(),"fuck off",Toast.LENGTH_SHORT).show();
//                    }
//                });


            }
        });
        return root;
    }
}
