package com.example.varantest.Match;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.varantest.HomeActivity;
import com.example.varantest.Mail.MailActivity;
import com.example.varantest.Match.Match_Tab_Layout.All_profiles_Fragment;
import com.example.varantest.Match.Match_Tab_Layout.Match_Tab_Adapter;
import com.example.varantest.Match.Match_Tab_Layout.Shortlisted;
import com.example.varantest.Match.Match_Tab_Layout.Shortlisted_me;
import com.example.varantest.R;
import com.example.varantest.Search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;


public class MatchActivity extends AppCompatActivity{

    TabLayout tablayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);



        tablayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpgr);


        /*----Start of Tab layout */

        Match_Tab_Adapter match_tab_adapter = new Match_Tab_Adapter(getSupportFragmentManager());
        match_tab_adapter.add_mail_parent_tab(new All_profiles_Fragment(),"All_profiles");
        match_tab_adapter.add_mail_parent_tab(new Shortlisted(),"Shortlisted");
        match_tab_adapter.add_mail_parent_tab(new Shortlisted_me(),"Shortlisted me");
        viewPager.setAdapter(match_tab_adapter);
        tablayout.setupWithViewPager(viewPager);

        /*----end of Tab layout */


        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_btm);

        /*----Start of bottom nav*/
        bottomNavigationView.setSelectedItemId(R.id.Profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), MatchActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Mail:
                        startActivity(new Intent(getApplicationContext(), MailActivity.class));
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.Search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });
        /*----End of bottom nav*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent Match_intent = new Intent(MatchActivity.this, HomeActivity.class);
        startActivity(Match_intent);
        overridePendingTransition(0, 0);
    }
}