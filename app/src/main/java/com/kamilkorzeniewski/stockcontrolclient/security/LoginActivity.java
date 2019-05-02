package com.kamilkorzeniewski.stockcontrolclient.security;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.kamilkorzeniewski.stockcontrolclient.MainActivity;
import com.kamilkorzeniewski.stockcontrolclient.R;
import com.kamilkorzeniewski.stockcontrolclient.retrofit.RestApiClient;

import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private TextInputEditText userNameInput;
    private TextInputEditText passwordInput;
    private Button loginButton;

    private final RestApiClient restApiClient = RestApiClient.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameInput = findViewById(R.id.login_name);
        passwordInput = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_send_button);


        userNameInput.addTextChangedListener(new MyTextWatcher());
        passwordInput.addTextChangedListener(new MyTextWatcher());

        final String TOKEN_KEY = getString(R.string.token_key);

        loginButton.setOnClickListener((view) -> {
            final String userNameText = userNameInput.getText().toString();
            final String passwordText = passwordInput.getText().toString();

            restApiClient.login(new AuthModel(userNameText, passwordText)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        String token = response.body();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(TOKEN_KEY, token);
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage("Wrong login or password \n " + response.message())
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("Login failure \n " + t.getMessage())
                            .setPositiveButton("OK", null)
                            .show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Are you sure to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> finishAffinity())
                .setNegativeButton("No", null)
                .show();
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count <= 0)
                LoginActivity.this.loginButton.setEnabled(false);
            else
                LoginActivity.this.loginButton.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {
            String result = s.toString().replaceAll(" ", "");
            if (!s.toString().equals(result)) {
                s.clear();
                s.append(result);
            }
        }
    }

}
