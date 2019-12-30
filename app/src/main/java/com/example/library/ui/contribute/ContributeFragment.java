package com.example.library.ui.contribute;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.library.Contribution;
import com.example.library.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import static android.app.Activity.RESULT_OK;

public class ContributeFragment extends Fragment {
    private String BookName, BookCategory , BookDescription, saveCurrentDate, saveCurrentTime, authorName;
    private String productRandomKey, downloadImageUrl;
    private Button AddNewBookButton;
    private EditText InputBookName, InputBookCategory , InputBookDescription, InputAuthorName;
    private ImageView InputBookImage;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    private DatabaseReference BookRef;
    private ProgressBar mProgressBar;
    private FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contribute, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        database = FirebaseDatabase.getInstance();
        BookRef = database.getReference().child("Books");


        AddNewBookButton =   root.findViewById(R.id.add_book_button);
        InputBookImage = root.findViewById(R.id.book_image);
        InputBookName =  root.findViewById(R.id.book_name);
        InputBookCategory= root.findViewById(R.id.book_category);
        InputBookDescription =  root.findViewById(R.id.book_description);
        InputAuthorName = root.findViewById(R.id.author);

        mProgressBar = root.findViewById(R.id.progressBar);

        InputBookImage.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        AddNewBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateBookData();
            }
        });
        return root;
    }
    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            InputBookImage.setImageURI(ImageUri);
        }
    }


    private void validateBookData()
    {
        BookName = InputBookName.getText().toString();
        BookCategory = InputBookCategory.getText().toString();
        BookDescription = InputBookDescription.getText().toString();
        authorName = InputAuthorName.getText().toString();
        if(ImageUri==null)
        {
            Toast.makeText(this.getActivity(), "Book image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(BookName))
        {
            Toast.makeText(this.getActivity(), "Book name is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(BookCategory))
        {
            Toast.makeText(this.getActivity(), "Book Category is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(BookDescription))
        {
            Toast.makeText(this.getActivity(), "Book Description is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(authorName))
        {
            Toast.makeText(this.getActivity(), "Author name is mandatory", Toast.LENGTH_SHORT).show();
        }
        else
        {
            storeBookInformation();
        }
    }

    private void storeBookInformation() {

        mProgressBar.setVisibility(View.VISIBLE);

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM,dd,yyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm::ss a" );
        saveCurrentTime = currentTime.format(calender.getTime());

        productRandomKey = saveCurrentDate+saveCurrentTime;


        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressBar.setVisibility(View.INVISIBLE);
                String message = e.toString();
                Toast.makeText(ContributeFragment.this.getActivity(), "Error"+message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(ContributeFragment.this.getActivity() , "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl= filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ContributeFragment.this.getActivity() , "Got the product image", Toast.LENGTH_SHORT).show();
                            downloadImageUrl= task.getResult().toString();
                            saveBookInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveBookInfoToDatabase()
    {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        BookRef.push().setValue(new Contribution(productRandomKey+user.getEmail(),BookName,BookCategory,authorName,BookDescription,user.getEmail(),downloadImageUrl,null)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ContributeFragment.this.getActivity() , "Book Added Successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(ContributeFragment.this.getActivity(),R.id.nav_host_fragment).navigate(R.id.nav_home);
                }
                else
                {
                    String message = task.getException().toString();
                    Toast.makeText(ContributeFragment.this.getActivity() , "Error:"+message, Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ContributeFragment.this.getActivity(),"asd",Toast.LENGTH_SHORT).show();
            }
        });
        //productMap.put()

//        BookRef.child(productRandomKey)
//                .updateChildren(new Contribution(BookName,BookCategory,authorName,BookDescription,downloadImageUrl)).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful())
//                {
//                    mProgressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(ContributeActivity.this , "Book Added Successfully", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    String message = task.getException().toString();
//                    Toast.makeText(ContributeActivity.this , "Error:"+message, Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

    }
}
