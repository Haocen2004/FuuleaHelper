package xyz.hellocraft.fuuleahelper.activity.data;

import org.json.JSONObject;

import xyz.hellocraft.fuuleahelper.activity.data.model.LoggedInUser;
import xyz.hellocraft.fuuleahelper.utils.Network;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            String feedback = Network.sendPost("https://api.fuulea.com/api/login/","username="+username+"&password="+password);
            JSONObject login_json = new JSONObject(feedback);
            if (login_json.getBoolean("authenticated")) {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                login_json.getString("userName"),
                                login_json.getString("displayName"));
                return new Result.Success<>(fakeUser);
            } else {
                return new Result.Error(new IOException("Wrong username or password"));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}