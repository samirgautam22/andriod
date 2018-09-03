package aayush.com.collegeproject.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aayush.com.collegeproject.Fragment.DashboardFragment;
import aayush.com.collegeproject.Fragment.LoginFragment;
import aayush.com.collegeproject.Fragment.SignFragment;
import aayush.com.collegeproject.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    private Fragment fragment;
    private boolean isHomeFirstTime = true;
    private TextView createNewAccount;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        createNewAccount=findViewById(R.id.link_signup);
        fragment = null;

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment("SIGNUP");
                createNewAccount.setVisibility(View.GONE);
            }
        });

    }

    private void loadFragment(String fragmentChooser){
        switch (fragmentChooser) {
            case "LOGIN":
                fragment = new LoginFragment();
                break;
            case "SIGNUP":
                fragment = new SignFragment();
                break;
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //add animation with this line
            // .setCustomAnimations(R.anim.card_flip_right_in,R.anim.card_flip_right_out);
            fragmentTransaction.replace(R.id.container, new DashboardFragment());
            fragmentTransaction.commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isHomeFirstTime) {
            fragment = new LoginFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new DashboardFragment());
            fragmentTransaction.commit();
            isHomeFirstTime = false;
        }
    }


    @Override
    public void onBackPressed() {
        createNewAccount.setVisibility(View.VISIBLE);
        loadFragment("LOGIN");
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            //  Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

}

