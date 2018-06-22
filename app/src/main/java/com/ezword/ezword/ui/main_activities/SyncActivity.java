package com.ezword.ezword.ui.main_activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ezword.ezword.R;
import com.ezword.ezword.background.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SyncActivity extends AppCompatActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        setTitle("Sync");
        switchSyncView();
    }

    private void switchSyncView() {
        setContentView(R.layout.activity_sync);

        ((TextView)findViewById(R.id.sync_status)).setText(User.NOT_SIGNED_IN);
        try {
            User.getInstance().isLoggedIn(loginCheckCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
        findViewById(R.id.sync_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLoginView();

            }
        });
        findViewById(R.id.sync_button).setClickable(false);
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
                } else try {
                    User.getInstance().signUp(username, password, signUpCallBack);
                } catch (IOException e) {
                    e.printStackTrace();
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

                try {
                    User.getInstance().logIn(username, password, loginCallback);
                } catch (IOException e) {
                    e.printStackTrace();
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

    private Callback signUpCallBack = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SyncActivity.this, "Can't SignUp with these information", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SyncActivity.this, "SignUp successfully. Please login to continue!", Toast.LENGTH_LONG).show();
                        switchLoginView();
                    }
                });
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SyncActivity.this, "Can't SignUp with these information", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    };

    private Callback loginCheckCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    TextView syncTV = findViewById(R.id.sync_status);
                    if (syncTV!= null)
                        syncTV.setText(User.NOT_SIGNED_IN);
                    findViewById(R.id.sync_button).setClickable(true);
                }
            });
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            final TextView syncTV = findViewById(R.id.sync_status);
            String message = null;
            boolean loggedInFlag = false;
            if (response.isSuccessful()) {
                String res = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String tokenCheck = jsonObject.getString("token-check");
                    if (tokenCheck.equals("success")) {
                        message = "Logged In";
                        loggedInFlag = true;
                    }
                    else
                        message = User.NOT_SIGNED_IN;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                message = User.NOT_SIGNED_IN;

            final boolean loggedIn = loggedInFlag;
            final String msg = message;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (syncTV != null)
                        syncTV.setText(msg);
                    if (loggedIn) {
                        findViewById(R.id.sync_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (User.getInstance().checkSynced()) {
                                    User.getInstance().uploadData();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(SyncActivity.this);
                                    builder.setTitle("Confirm Sync")
                                            .setMessage("Do you want to upload data or download data")
                                            .setPositiveButton("Upload Data", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    User.getInstance().uploadData();
                                                }
                                            })
                                            .setNegativeButton("Download Data", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    User.getInstance().downloadData();
                                                }
                                            })
                                            .create()
                                            .show();
                                }
                            }
                        });
                    } else {
                        findViewById(R.id.sync_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switchLoginView();
                            }
                        });
                    }
                }
            });
        }
    };
    private Callback loginCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                String json = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    User.getInstance().setToken(jsonObject.getString("token"));
                    User.getInstance().setLastSignInUsername(jsonObject.getString("username"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SyncActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                        switchSyncView();
                    }
                });
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SyncActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    };
}
