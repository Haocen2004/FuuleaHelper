package xyz.hellocraft.fuuleahelper.ui.home;

import static xyz.hellocraft.fuuleahelper.utils.Constant.HAS_LOGIN;
import static xyz.hellocraft.fuuleahelper.utils.Constant.TOKEN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import xyz.hellocraft.fuuleahelper.activity.LoginActivity;
import xyz.hellocraft.fuuleahelper.databinding.FragmentHomeBinding;
import xyz.hellocraft.fuuleahelper.utils.Logger;
import xyz.hellocraft.fuuleahelper.utils.Network;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        binding.buttonLogin.setVisibility(View.INVISIBLE);
        refAccount(homeViewModel);
        binding.buttonLogin.setOnClickListener(view -> launcher.launch(true));
        return root;
    }

    private void onNotLogin() {
        main_handle.post(ref_runnable);
//        binding.buttonLogin.setText("登陆账号");
    }

    Handler main_handle = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    Runnable ref_runnable = new Runnable() {
        @Override
        public void run() {
            preferences.edit().putString("sid","").apply();
            new ViewModelProvider(requireActivity()).get(HomeViewModel.class).setText("账号未登录");
            binding.buttonLogin.setVisibility(View.VISIBLE);
            binding.buttonLogin.setText("登陆账号");
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void refAccount(HomeViewModel homeViewModel) {
        if (HAS_LOGIN) return;
        if (preferences.contains("sid")) {
            Runnable runnable = () -> {
                Logger.d("SID",preferences.getString("sid",""));
                HashMap<String, String> map = new HashMap<>();
                map.put("Cookie", "sessionid="+preferences.getString("sid",""));
                String feedback = Network.sendGet("https://api.fuulea.com/api/login/check/", map);
                Logger.d("login",feedback);
                JSONObject login_json;
                try {
                    login_json = new JSONObject(feedback);
                    homeViewModel.setText("欢迎\n"+login_json.getString("displayName"));
                    TOKEN = "jwt "+login_json.getString("token");
                    HAS_LOGIN = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    onNotLogin();
                }
            };
            new Thread(runnable).start();
        } else {
            onNotLogin();
        }
    }


    ActivityResultLauncher<Boolean> launcher = registerForActivityResult(new ResultContract(), result -> {

        if (result.equals("OK")) {
            refAccount(new ViewModelProvider(this).get(HomeViewModel.class));
        }

    });


    class ResultContract extends ActivityResultContract<Boolean, String> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Boolean input) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra("b", input);
            return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            return Objects.requireNonNull(intent).getStringExtra("s");
        }
    }

}