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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerDetail extends AppCompatActivity {
    private String id;
    private MHandler handler = new MHandler(this);
    private TextView text_name;
    private TextView text_level;
    private TextView text_status;
    private TextView text_tel;
    private TextView text_mail;
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
        text_name = (TextView) findViewById(R.id.product_detail_name);
        text_level = (TextView) findViewById(R.id.product_detail_number);
        text_status = (TextView) findViewById(R.id.product_detail_price);
        text_tel = (TextView) findViewById(R.id.product_detail_unit);
        text_mail = (TextView) findViewById(R.id.product_detail_class);
        text_addr = (TextView) findViewById(R.id.product_detail_intro);
    }

    private void showDetail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/customer_query_json?cusotmerid=" + id);
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
                                single.getString("customertype") + ";" +
                                single.getString("customerstatus") + ";" +
                                single.getString("telephone") + ";" +
                                single.getString("email") + ";" +
                                single.getString("address");
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Log.e("Customer_Detail", "Get detail failed.");
                }
            }
        }).start();
    }

    private class MHandler extends Handler {
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
                text_name.setText(data[0]);
                text_level.setText(data[1]);
                text_status.setText(data[2]);
                text_tel.setText(data[3]);
                text_mail.setText(data[4]);
                text_addr.setText(data[5]);
            }
        }
    }
}
