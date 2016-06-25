package stk.mobileoffice.contact;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import org.json.JSONObject;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ContactDetail extends AppCompatActivity {
    private String id;
    private MHandler handler = new MHandler(this);
    private Thread thread;
    private TextView text_name;
    private TextView text_mobile;
    private TextView text_tel;
    private TextView text_mail;
    private TextView text_age;
    private TextView text_gender;
    private TextView text_addr;
//    private ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);
        id = getIntent().getStringExtra("_id");
        initView();
        showDetail();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        text_name = (TextView) findViewById(R.id.contact_detail_name);
        text_mobile = (TextView) findViewById(R.id.contact_detail_mobile);
        text_tel = (TextView) findViewById(R.id.contact_detail_tel);
        text_mail = (TextView) findViewById(R.id.contact_detail_mail);
        text_age = (TextView) findViewById(R.id.contact_detail_age);
        text_gender = (TextView) findViewById(R.id.contact_detail_gender);
        text_addr = (TextView) findViewById(R.id.contact_detail_addr);
//        pic = (ImageView) findViewById(R.id.contact_detail_pic);
    }

    private void showDetail() {
        thread = new Thread(runnable);
        thread.start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/contact_query_json?contactid=" + id);
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
                    Log.i("Contact_Detail_Data", result.toString());
                    JSONObject single = new JSONObject(result.toString()).getJSONObject("0");
                    Message msg = new Message();
                    String str = single.getString("contactsname") + ";" +
                            single.getString("contactsmobile") + ";" +
                            single.getString("contactstelephone") + ";" +
                            single.getString("contactsemail") + ";" +
                            single.getString("contactsage") + ";" +
                            single.getString("contactsgender") + ";" +
                            single.getString("contactsaddress");
                    Bitmap bmp = null;
                        /*try {
                            URL bmpUrl = new URL(single.getString("profile").replace("\\", ""));
                            HttpURLConnection connection = (HttpURLConnection) bmpUrl.openConnection();
                            connection.setDoInput(true);
                            connection.setUseCaches(false);
                            connection.connect();
                            InputStream inputStream = connection.getInputStream();
                            bmp = BitmapFactory.decodeStream(inputStream);
                        } catch (Exception e1) {
                            Log.e("Contact_Detail", "Get picture failed.");
                        }*/
                    msg.obj = new Object[]{str, bmp};
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                Log.e("Contact_Detail", "Get detail failed.");
            }
        }
    };

    private static class MHandler extends Handler {
        private final WeakReference<ContactDetail> outer;
        MHandler(ContactDetail target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            ContactDetail target = outer.get();
            if (target != null) {
                Object[] raw = (Object[]) msg.obj;
                String[] data = ((String) raw[0]).split(";", -1);
                target.getSupportActionBar().setTitle(data[0]);
                target.text_name.setText(data[0]);
                target.text_mobile.setText(data[1]);
                target.text_tel.setText(data[2]);
                target.text_mail.setText(data[3]);
                target.text_age.setText(data[4]);
                target.text_gender.setText(data[5]);
                target.text_addr.setText(data[6]);
                /*Bitmap bmp = (Bitmap) raw[1];
                if (bmp != null)
                    target.pic.setImageBitmap(bmp);*/
            }
        }
    }
}
