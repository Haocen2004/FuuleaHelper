package xyz.hellocraft.fuuleahelper.activity;

import static xyz.hellocraft.fuuleahelper.utils.Constant.TOKEN;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.hellocraft.fuuleahelper.databinding.ActivityLoginBinding;
import xyz.hellocraft.fuuleahelper.utils.Logger;
import xyz.hellocraft.fuuleahelper.utils.Network;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private String username;
    private String password;
    private Logger Log;
    private final String TAG = "LoginActivity";
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            boolean t1, t2;
            t1 = binding.editTextUserName.getText().toString().isEmpty();
            t2 = binding.editTextPassword.getText().toString().isEmpty();
            binding.buttonLogin.setEnabled(!t1 && !t2);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        binding.editTextUserName.addTextChangedListener(textWatcher);
        binding.editTextPassword.addTextChangedListener(textWatcher);
        binding.buttonLogin.setOnClickListener(this);
        Log = Logger.getLogger(this);
        setContentView(binding.getRoot());
    }

    @Override
    public void onClick(View view) {
        binding.progessBarUser.setVisibility(View.VISIBLE);
        binding.buttonLogin.setEnabled(false);
        username = binding.editTextUserName.getText().toString();
        password = binding.editTextPassword.getText().toString();
        new Thread(login_runnable).start();

    }

    Handler login_handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String feedback = data.getString("value");
            try {
                JSONObject login_json = new JSONObject(feedback);
                if (login_json.getBoolean("authenticated")) {
                    TOKEN = "jwt " + login_json.getString("token");
                    getSharedPreferences("data", Context.MODE_PRIVATE).edit().putString("sid", login_json.getString("sid")).apply();
                    setResult(RESULT_OK,new Intent().putExtra("s","OK"));
                    finish();
                } else {
                    binding.progessBarUser.setVisibility(View.INVISIBLE);
                    binding.buttonLogin.setEnabled(true);
                    Log.makeToast(login_json.getString("msg"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    Runnable login_runnable = () -> {
        String feedback = Network.sendLoginPost("https://api.fuulea.com/api/login/","username="+username+"&password="+password);
        Logger.d(TAG,feedback);
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString("value", feedback);
        msg.setData(data);
        login_handler.sendMessage(msg);
    };
}