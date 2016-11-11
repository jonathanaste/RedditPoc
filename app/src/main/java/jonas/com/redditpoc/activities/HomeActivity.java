package jonas.com.redditpoc.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.fragments.TopFragment;
import jonas.com.redditpoc.interfaces.ActivityCallback;

public class HomeActivity extends AppCompatActivity implements ActivityCallback {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setIcon(R.drawable.reddit);
        }

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, new TopFragment()).commit();
    }

    @Override
    public void onItemSelected(Fragment fragment) {
        replaceFragment(fragment,true);
    }

    protected void replaceFragment(Fragment newFragment, boolean addToBackStack) {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(newFragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(R.id.content, newFragment).commit();
    }

}
