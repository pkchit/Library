package com.example.library;

import android.content.Intent;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Button logoutButton;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private NavigationView navigationView;
    private ImageView profile_pic;
    private LinearLayoutManager linearLayoutManager;
    private DrawerLayout drawer;
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);



        mAuth= getInstance();
//        user = mAuth.getCurrentUser();
//        profile_pic=findViewById(R.id.profile_image);
//        profile_pic.setImageURI(user.getPhotoUrl());

        //FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

       drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_requests, R.id.nav_slideshow,R.id.nav_contribute,
                R.id.nav_tools, R.id.nav_inventory)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_requests:
                        Navigation.findNavController(NavigationActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_requests);
                        break;
                    case R.id.nav_home:Navigation.findNavController(NavigationActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_home);
                        break;
                    case R.id.nav_account_settings:Navigation.findNavController(NavigationActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_tools);
                        break;
                    case R.id.nav_contribute:
                        Navigation.findNavController(NavigationActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_contribute);
                        break;
                    case R.id.nav_inventory:
                        Navigation.findNavController(NavigationActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_inventory);
                        break;
                }
                menuItem.setChecked(true);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Firebase sign out
                getInstance().signOut();
                Intent home = new Intent(NavigationActivity.this, MainActivity.class);
                startActivity(home);
            }
        });


        navigationView= findViewById(R.id.nav_view);
//        MenuItem contribute = navigationView.getMenu().findItem(R.id.nav_contribute);
//        contribute.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Intent cont = new Intent(NavigationActivity.this, ContributeActivity.class);
//                startActivity(cont);
//                return true;
//            }
//        });
        View headerView = navigationView.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.profile_name);

        CircleImageView profileImageView=headerView.findViewById(R.id.profile_image);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration);

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
