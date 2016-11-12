package jonas.com.redditpoc.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import jonas.com.redditpoc.R;
import jonas.com.redditpoc.ui.fragments.TopFragment;
import jonas.com.redditpoc.interfaces.ActivityCallback;

public class HomeActivity extends AppCompatActivity implements ActivityCallback {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setIcon(R.drawable.reddit);
        }
        fragmentManager = getSupportFragmentManager();

        // Finding the fragment that we want to show
        Fragment fragment = fragmentManager.findFragmentByTag(TopFragment.TAG);

        // If the fragment is null it means that we already have the fragment alive
        if (fragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content, TopFragment.newInstance(), TopFragment.TAG).commit();
        }
    }

    @Override
    public void onItemSelected(Fragment fragment) {
        replaceFragment(fragment, true);
    }

    private void replaceFragment(Fragment newFragment, boolean addToBackStack) {
        String fragmentTag = newFragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragmentTag);
        }
        fragmentTransaction.replace(R.id.content, newFragment, fragmentTag).commit();
    }
}
