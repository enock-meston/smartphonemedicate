package com.nigoote.smartphonemedicate.ClientFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.nigoote.smartphonemedicate.AddPersonFragment;
import com.nigoote.smartphonemedicate.AddPillsFragment;
import com.nigoote.smartphonemedicate.HomeFragment;
import com.nigoote.smartphonemedicate.ListPetientFragment;
import com.nigoote.smartphonemedicate.MainActivity;
import com.nigoote.smartphonemedicate.R;

public class PetientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petient);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer2 = findViewById(R.id.drawer_layout2);
        NavigationView navigationView =  findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(PetientActivity.this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer2,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer2.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, new HomePetientFragment()).commit();
            navigationView.setCheckedItem(R.id.petienthome);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.petienthome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new HomePetientFragment()).commit();
                break;
            case R.id.settimemenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new SetTimeFragment()).commit();
                break;
            case R.id.mypills:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,new ViewAvailablePillsFragment()).commit();
                break;
            case R.id.logoutmenu2:
                final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.prefLoginState),"loggedout");
                editor.apply();
                startActivity((new Intent(PetientActivity.this, MainActivity.class)));
                finish();
                return true;
        }
        drawer2.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer2.isDrawerOpen(GravityCompat.START)){
            drawer2.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}