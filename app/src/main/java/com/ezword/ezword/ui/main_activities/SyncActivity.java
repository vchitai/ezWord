package com.ezword.ezword.ui.main_activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ezword.ezword.R;
import com.ezword.ezword.background.user.User;

import static com.ezword.ezword.background.user.User.CD_NOT_SIGNED_IN;

public class SyncActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchSyncView();
    }

    private void switchSyncView() {
        setContentView(R.layout.activity_sync);

        ((TextView)findViewById(R.id.sync_status)).setText(User.getInstance().getLastSyncStatus());

        findViewById(R.id.sync_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (User.getInstance().getSyncStatus() == CD_NOT_SIGNED_IN) {
                    switchLoginView();
                }
            }
        });
    }

    private void switchSignUpView() {
        setContentView(R.layout.activity_signup);
        findViewById(R.id.sign_up_sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((TextInputEditText)findViewById(R.id.sign_up_username_tiet)).getText().toString();
                String password = ((TextInputEditText)findViewById(R.id.sign_up_password_tiet)).getText().toString();
                String re_password = ((TextInputEditText)findViewById(R.id.sign_up_re_password_tiet)).getText().toString();

                if (!password.equals(re_password)) {
                    Toast.makeText(SyncActivity.this, "Password not match", Toast.LENGTH_LONG).show();
                } else if (User.getInstance().signUp(username, password)) {
                    switchLoginView();
                } else {
                    Toast.makeText(SyncActivity.this, "SignUp Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.sign_up_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLoginView();
            }
        });
    }

    private void switchLoginView() {
        setContentView(R.layout.activity_login);
        findViewById(R.id.login_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((TextInputEditText)findViewById(R.id.login_username_tiet)).getText().toString();
                String password = ((TextInputEditText)findViewById(R.id.login_password_tiet)).getText().toString();
                if (User.getInstance().logIn(username, password)) {
                    switchSyncView();
                } else {
                    Toast.makeText(SyncActivity.this, "Login Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.login_sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchSignUpView();
            }
        });
    }
}
