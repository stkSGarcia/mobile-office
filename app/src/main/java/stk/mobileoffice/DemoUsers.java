package stk.mobileoffice;

import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: stk
 * Date: 2016/6/24
 * Time: 22:35
 */
public enum DemoUsers {
    A(100, "q", "q"), B(200, "a", "a");

    private int id;
    private String username;
    private String password;
    DemoUsers(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static boolean login(String name, String pw) {
        for (DemoUsers i : DemoUsers.values()) {
            if (name.equals(i.username) && pw.equals(i.password)) {
                CurrentUser.id = i.id;
                setName();
                return true;
            }
        }
        return false;
    }

    private static void setName() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/staff_query_json?staffid=" + CurrentUser.id);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder result = new StringBuilder();
                        String s;
                        while ((s = in.readLine()) != null)
                            result.append(s);
                        Log.i("name", result.toString());
                        JSONObject single = new JSONObject(result.toString()).getJSONObject("0");
                        CurrentUser.name = single.getString("name");
                    }
                } catch (Exception e) {
                    Log.e("name", "Get name failed.");
                }
            }
        }).start();
    }
}
