package stk.mobileoffice.opportunity;

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

public class OpportunityDetail extends AppCompatActivity {
    private String id;
    private String customerName;
    private MHandler handler = new MHandler(this);
    private Thread thread;
    private TextView text_name;
    private TextView text_amount;
    private TextView text_status;
    private TextView text_type;
    private TextView text_customer_name;
    private TextView text_rate;
    private TextView text_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunity_detail);
        String[] raw = getIntent().getStringExtra("msg").split(";", -1);
        id = raw[0];
        customerName = raw[1];
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
        text_name = (TextView) findViewById(R.id.opportunity_detail_name);
        text_amount = (TextView) findViewById(R.id.opportunity_detail_amount);
        text_status = (TextView) findViewById(R.id.opportunity_detail_status);
        text_type = (TextView) findViewById(R.id.opportunity_detail_type);
        text_customer_name = (TextView) findViewById(R.id.opportunity_detail_customer_name);
        text_rate = (TextView) findViewById(R.id.opportunity_detail_rate);
        text_date = (TextView) findViewById(R.id.opportunity_detail_date);
    }

    private void showDetail() {
        thread = new Thread(runnable);
        thread.start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/opportunity_query_json?opportunityid=" + id);
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
                    Log.i("Opportunity_Detail_Data", result.toString());
                    JSONObject single = new JSONObject(result.toString()).getJSONObject("0");
                    Message msg = new Message();
                    msg.obj = single.getString("opportunitytitle") + ";" +
                            single.getString("estimatedamount") + " 元;" +
                            TypeMap.getOpportunityType(single.getString("opportunitystatus")) + ";" +
                            TypeMap.getBusniessType(single.getString("businesstype")) + ";" +
                            single.getString("successrate") + ";" +
                            single.getString("expecteddate") + ";" +
                            customerName;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                Log.e("Opportunity_Detail", "Get detail failed.");
            }
        }
    };

    private static class MHandler extends Handler {
        private final WeakReference<OpportunityDetail> outer;
        MHandler(OpportunityDetail target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            OpportunityDetail target = outer.get();
            if (target != null) {
                String raw = (String) msg.obj;
                String[] data = raw.split(";", -1);
                target.getSupportActionBar().setTitle(data[0]);
                target.text_name.setText(data[0]);
                target.text_amount.setText(data[1]);
                target.text_status.setText(data[2]);
                target.text_type.setText(data[3]);
                target.text_rate.setText(data[4]);
                target.text_date.setText(data[5]);
                target.text_customer_name.setText(data[6]);
            }
        }
    }
}
