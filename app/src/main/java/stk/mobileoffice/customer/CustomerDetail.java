package stk.mobileoffice.customer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import org.json.JSONObject;
import stk.mobileoffice.R;
import stk.mobileoffice.TypeMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerDetail extends AppCompatActivity {
    private String id;
    private MHandler handler = new MHandler(this);
    private Thread thread;
    private TextView text_name;
    private TextView text_level;
    private TextView text_tel;
    private TextView text_mail;
    private TextView text_web;
    private TextView text_addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);
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
        text_name = (TextView) findViewById(R.id.customer_detail_name);
        text_level = (TextView) findViewById(R.id.customer_detail_level);
        text_tel = (TextView) findViewById(R.id.customer_detail_tel);
        text_mail = (TextView) findViewById(R.id.customer_detail_mail);
        text_web = (TextView) findViewById(R.id.customer_detail_web);
        text_addr = (TextView) findViewById(R.id.customer_detail_addr);
    }

    private void showDetail() {
        thread = new Thread(runnable);
        thread.start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/customer_query_json?customerid=" + id);
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
                    Log.i("Customer_Detail_Data", result.toString());
                    JSONObject single = new JSONObject(result.toString()).getJSONObject("0");
                    Message msg = new Message();
                    msg.obj = single.getString("customername") + ";" +
                            TypeMap.getCustomerType(single.getString("customertype")) + ";" +
                            single.getString("telephone") + ";" +
                            single.getString("email") + ";" +
                            single.getString("website") + ";" +
                            single.getString("address");
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                Log.e("Customer_Detail", "Get detail failed.");
            }
        }
    };

    private static class MHandler extends Handler {
        private final WeakReference<CustomerDetail> outer;
        MHandler(CustomerDetail target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            CustomerDetail target = outer.get();
            if (target != null) {
                String raw = (String) msg.obj;
                String[] data = raw.split(";", -1);
                target.getSupportActionBar().setTitle(data[0]);
                target.text_name.setText(data[0]);
                target.text_level.setText(data[1]);
                target.text_tel.setText(data[2]);
                target.text_mail.setText(data[3]);
                target.text_web.setText(data[4]);
                target.text_addr.setText(data[5]);
            }
        }
    }
}
