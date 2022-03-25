package xyz.hellocraft.fuuleahelper.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import xyz.hellocraft.fuuleahelper.ui.login.LoginActivity;
import xyz.hellocraft.fuuleahelper.databinding.FragmentHomeBinding;
import xyz.hellocraft.fuuleahelper.utils.Logger;
import xyz.hellocraft.fuuleahelper.utils.Network;

import static xyz.hellocraft.fuuleahelper.utils.Constant.TOKEN;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        if (preferences.contains("sid")) {
            Runnable runnable = () -> {

                HashMap<String, String> map = new HashMap<>();
                map.put("Cookie", "sessionid=");
                String feedback = Network.sendGet("https://api.fuulea.com/api/login/check/", map);
                Logger.d("login",feedback);
                JSONObject login_json;
                try {
                    login_json = new JSONObject(feedback);
                    homeViewModel.setText(login_json.getString("displayName"));
                    TOKEN = "jwt "+login_json.getString("token");
    //                JSONArray subject_array = login_json.getJSONArray("subjectList");
    //                Map<Integer, String> subject_map = new HashMap<Integer, String>();
    //                for (int i = 0; i < subject_array.length(); i++) {
    //                    JSONObject temp_json = subject_array.getJSONObject(i);
    //                    subject_map.put(temp_json.getInt("id"),temp_json.getString("logo"));
    //                }
    //                SUBJECT_MAP = subject_map;
                } catch (JSONException e) {
                    e.printStackTrace();
                    onNotLogin();
                }
            };
            new Thread(runnable).start();
        } else {
            onNotLogin();
        }
        binding.buttonLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);

        });

        return root;
    }

    private void onNotLogin() {
        preferences.edit().putString("sid","").apply();
        new ViewModelProvider(this).get(HomeViewModel.class).setText("账号未登录");
        binding.buttonLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}