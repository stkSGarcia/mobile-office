package stk.mobileoffice.info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONObject;
import stk.mobileoffice.CurrentUser;
import stk.mobileoffice.Login;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: stk
 * Date: 2016/6/25
 * Time: 15:26
 */
public class Info extends Fragment {
    private View view;
    private Thread thread;
    private MHandler handler = new MHandler(this);
    private TextView text_name;
    private TextView text_gender;
    private TextView text_position;
    private TextView text_order;
    private TextView text_mobile;
    private TextView text_tel;
    private TextView text_mail;
    private ImageView pic;
    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("个人信息");
        text_name = (TextView) view.findViewById(R.id.info_name);
        text_gender = (TextView) view.findViewById(R.id.info_gender);
        text_position = (TextView) view.findViewById(R.id.info_position);
        text_order = (TextView) view.findViewById(R.id.info_order);
        text_mobile = (TextView) view.findViewById(R.id.info_mobile);
        text_tel = (TextView) view.findViewById(R.id.info_tel);
        text_mail = (TextView) view.findViewById(R.id.info_mail);
        pic = (ImageView) view.findViewById(R.id.info_pic);
        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        thread = new Thread(runnable);
        thread.start();
        return view;
    }

    private Runnable runnable = new Runnable() {
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
                    Log.i("info_Data", result.toString());
                    JSONObject single = new JSONObject(result.toString()).getJSONObject("0");
                    Message msg = new Message();
                    String str = single.getString("name") + ";" +
                            single.getString("gender") + ";" +
                            single.getString("position") + ";" +
                            single.getString("order") + ";" +
                            single.getString("mobile") + ";" +
                            single.getString("tel") + ";" +
                            single.getString("email");
                    Bitmap bmp = null;
                    try {
                        URL bmpUrl = new URL(single.getString("avatar").replace("\\", ""));
                        HttpURLConnection connection = (HttpURLConnection) bmpUrl.openConnection();
                        connection.setDoInput(true);
                        connection.setUseCaches(false);
                        connection.connect();
                        InputStream inputStream = connection.getInputStream();
                        bmp = BitmapFactory.decodeStream(inputStream);
                    } catch (Exception e1) {
                        Log.e("Info_Data", "Get picture failed.");
                    }
                    msg.obj = new Object[]{str, bmp};
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                Log.e("info", "Get detail failed.");
            }
        }
    };

    private static class MHandler extends Handler {
        private final WeakReference<Info> outer;
        MHandler(Info target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            Info target = outer.get();
            if (target != null) {
                Object[] raw = (Object[]) msg.obj;
                String[] data = ((String) raw[0]).split(";", -1);
                target.text_name.setText(data[0]);
                target.text_gender.setText(data[1]);
                target.text_position.setText(data[2]);
                target.text_order.setText(data[3]);
                target.text_mobile.setText(data[4]);
                target.text_tel.setText(data[5]);
                target.text_mail.setText(data[6]);
                Bitmap bmp = (Bitmap) raw[1];
                if (bmp != null)
                    target.pic.setImageBitmap(bmp);
            }
        }
    }
}
