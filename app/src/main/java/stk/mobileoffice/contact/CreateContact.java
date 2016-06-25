package stk.mobileoffice.contact;

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
 * Time: 17:53
 */
public class CreateContact extends AppCompatActivity {
    private MHandler handler = new MHandler(this);
    private String customerId;
    private EditText text_name;
    private EditText text_mobile;
    private EditText text_tel;
    private EditText text_mail;
    private EditText text_age;
    private EditText text_gender;
    private EditText text_addr;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_create);
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
                    URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/contact_create_json");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    String str = "customerid=" + customerId +
                            "&staffid=" + CurrentUser.id +
                            "&contactsname=" + text_name.getText().toString() +
                            "&contactsmobile=" + text_mobile.getText().toString() +
                            "&contactstelephone=" + text_tel.getText().toString() +
                            "&contactsemail=" + text_mail.getText().toString() +
                            "&contactsage=" + text_age.getText().toString() +
                            "&contactsgender=" + text_gender.getText().toString() +
                            "&contactsaddress=" + text_addr.getText().toString();
                    byte[] strData = str.getBytes("UTF-8");
                    OutputStream out = con.getOutputStream();
                    out.write(strData);
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder result = new StringBuilder();
                        String s;
                        while ((s = in.readLine()) != null)
                            result.append(s);
                        Log.i("Contact_Create_Data", result.toString());
                        if (new JSONObject(result.toString()).getInt("resultcode") == 0) {
                            Message msg = new Message();
                            msg.obj = "创建成功";
                            handler.sendMessage(msg);
                            CreateContact.this.finish();
                        } else {
                            Message msg = new Message();
                            msg.obj = "创建失败";
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Contact_Create", "Create failed.");
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
        text_name = (EditText) findViewById(R.id.contact_create_name);
        text_mobile = (EditText) findViewById(R.id.contact_create_mobile);
        text_tel = (EditText) findViewById(R.id.contact_create_tel);
        text_mail = (EditText) findViewById(R.id.contact_create_mail);
        text_age = (EditText) findViewById(R.id.contact_create_age);
        text_gender = (EditText) findViewById(R.id.contact_create_gender);
        text_addr = (EditText) findViewById(R.id.contact_create_addr);
        confirm = (Button) findViewById(R.id.contact_create_confirm);
    }

    private static class MHandler extends Handler {
        private final WeakReference<CreateContact> outer;
        MHandler(CreateContact target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            CreateContact target = outer.get();
            if (target != null) {
                String str = (String) msg.obj;
                Toast.makeText(target, str, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
