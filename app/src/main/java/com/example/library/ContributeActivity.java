package com.example.library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ContributeActivity extends AppCompatActivity
{

    private String BookName, BookCategory , BookDescription, saveCurrentDate, saveCurrentTime;
    private String productRandomKey, downloadImageUrl;
    private Button AddNewBookButton;
    private EditText InputBookName, InputBookCategory , InputBookDescription;
    private ImageView InputBookImage;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    private DatabaseReference BookRef;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        BookRef = FirebaseDatabase.getInstance().getReference().child("Books");

        AddNewBookButton =   findViewById(R.id.add_book_button);
        InputBookImage = findViewById(R.id.book_image);
        InputBookName =  findViewById(R.id.book_name);
        InputBookCategory= findViewById(R.id.book_category);
        InputBookDescription =  findViewById(R.id.book_description);

        mProgressBar = findViewById(R.id.progressBar);

        InputBookImage.setOnClickListener(new View.OnClickListener() {
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
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        if(ImageUri==null)
        {
            Toast.makeText(this, "Book image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(BookName))
        {
            Toast.makeText(this, "Book name is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(BookCategory))
        {
            Toast.makeText(this, "Book Category is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(BookDescription))
        {
            Toast.makeText(this, "Book Description is mandatory", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ContributeActivity.this , "Error"+message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(ContributeActivity.this , "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(ContributeActivity.this , "Got the product image", Toast.LENGTH_SHORT).show();

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
        String email = mAuth.getUid();
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("Bid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("name", BookName);
        productMap.put("category", BookCategory);
        productMap.put("description", BookDescription);
        productMap.put("image", downloadImageUrl);

        BookRef.child(productRandomKey)
                .updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ContributeActivity.this , "Book Added Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String message = task.getException().toString();
                    Toast.makeText(ContributeActivity.this , "Error:"+message, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


}
