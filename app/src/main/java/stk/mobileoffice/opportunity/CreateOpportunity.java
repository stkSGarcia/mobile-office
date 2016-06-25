package stk.mobileoffice.opportunity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import stk.mobileoffice.CurrentUser;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Author: stk
 * Date: 2016/6/25
 * Time: 17:52
 */
public class CreateOpportunity extends AppCompatActivity {
    private MHandler handler = new MHandler(this);
    private String customerId;
    private EditText text_name;
    private EditText text_amount;
    private EditText text_type;
    private EditText text_rate;
    private EditText text_date;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunity_create);
        customerId = getIntent().getStringExtra("customerid");
        initView();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
    }

    private void create() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/opportunity_create_json");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    String str = "staffid"+ CurrentUser.id +
                            "&customerid"+ customerId +
                            "&opportunitytitle=" + text_name.getText().toString() +
                            "&estimatedamount=" + text_amount.getText().toString() +
                            "&businesstype=" + text_type.getText().toString() +
                            "&successrate=" + text_rate.getText().toString() +
                            "&expecteddate=" + text_date.getText().toString();
                    byte[] strData = str.getBytes("UTF-8");
                    OutputStream out = con.getOutputStream();
                    out.write(strData);
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder result = new StringBuilder();
                        String s;
                        while ((s = in.readLine()) != null)
                            result.append(s);
                        Log.i("Opportunity_Create_Data", result.toString());
                        if (new JSONObject(result.toString()).getInt("resultcode") == 0) {
                            Message msg = new Message();
                            msg.obj = "创建成功";
                            handler.sendMessage(msg);
                            CreateOpportunity.this.finish();
                        } else {
                            Message msg = new Message();
                            msg.obj = "创建失败";
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Opportunity_Create", "Create failed.");
                    Message msg = new Message();
                    msg.obj = "无法创建";
                    handler.sendMessage(msg);
                }
            }
        }).start();
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
        text_name = (EditText) findViewById(R.id.opportunity_create_name);
        text_amount = (EditText) findViewById(R.id.opportunity_create_amount);
        text_type = (EditText) findViewById(R.id.opportunity_create_type);
        text_rate = (EditText) findViewById(R.id.opportunity_create_rate);
        text_date = (EditText) findViewById(R.id.opportunity_create_date);
        confirm = (Button) findViewById(R.id.opportunity_create_confirm);
    }

    private static class MHandler extends Handler {
        private final WeakReference<CreateOpportunity> outer;
        MHandler(CreateOpportunity target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            CreateOpportunity target = outer.get();
            if (target != null) {
                String str = (String) msg.obj;
                Toast.makeText(target, str, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
