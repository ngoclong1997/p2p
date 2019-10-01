package com.p2p.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.p2p.fragments.JavisFragment;
import com.p2p.fragments.ProfileFragment;
import com.p2p.fragments.QuotationFragment;
import com.p2p.R;
import com.p2p.fragments.WalletFragment;

public class MainActivity extends AppCompatActivity {

    final Fragment walletFragment = new WalletFragment();
    final Fragment javisFragment = new JavisFragment();
    final Fragment quotationFragment = new QuotationFragment();
    final Fragment profileFragment = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment activeFragment = walletFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);
        fm.beginTransaction().add(R.id.frag_container, profileFragment, "4").hide(profileFragment).commit();
        fm.beginTransaction().add(R.id.frag_container, quotationFragment, "3").hide(quotationFragment).commit();
        fm.beginTransaction().add(R.id.frag_container,javisFragment, "2").hide(javisFragment).commit();
        fm.beginTransaction().add(R.id.frag_container,walletFragment, "1").commit();
//        fm.beginTransaction().replace(R.id.frag_container, new JavisFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_wallet:
                    fm.beginTransaction().hide(activeFragment).show(walletFragment).commit();
                    activeFragment = walletFragment;
                    return true;
                case R.id.nav_javis:
                    fm.beginTransaction().hide(activeFragment).show(javisFragment).commit();
                    activeFragment = javisFragment;
                    return true;
                case R.id.nav_quotation:
                    fm.beginTransaction().hide(activeFragment).show(quotationFragment).commit();
                    activeFragment = quotationFragment;
                    return true;
                case R.id.nav_profile:
                    fm.beginTransaction().hide(activeFragment).show(profileFragment).commit();
                    activeFragment = profileFragment;
                    return true;
            }
            return false;
        }
    };
}
