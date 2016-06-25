package stk.mobileoffice.product;

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
 * Time: 17:47
 */
public class CreateProduct extends AppCompatActivity {
    private MHandler handler = new MHandler(this);
    private EditText text_name;
    private EditText text_number;
    private EditText text_price;
    private EditText text_unit;
    private EditText text_class;
    private EditText text_intro;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_create);
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
                    URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/product_create_json");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    String str = "productname=" + text_name.getText().toString() +
                            "&productsn=" + text_number.getText().toString() +
                            "&standardprice=" + text_price.getText().toString() +
                            "&salesunit=" + text_unit.getText().toString() +
                            "&classification=" + text_class.getText().toString() +
                            "&introduction=" + text_intro.getText().toString();
                    byte[] strData = str.getBytes("UTF-8");
                    OutputStream out = con.getOutputStream();
                    out.write(strData);
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder result = new StringBuilder();
                        String s;
                        while ((s = in.readLine()) != null)
                            result.append(s);
                        Log.i("Product_Create_Data", result.toString());
                        if (new JSONObject(result.toString()).getInt("resultcode") == 0) {
                            Message msg = new Message();
                            msg.obj = "创建成功";
                            handler.sendMessage(msg);
                            CreateProduct.this.finish();
                        } else {
                            Message msg = new Message();
                            msg.obj = "创建失败";
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Product_Create", "Create failed.");
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
        text_name = (EditText) findViewById(R.id.product_create_name);
        text_number = (EditText) findViewById(R.id.product_create_number);
        text_price = (EditText) findViewById(R.id.product_create_price);
        text_unit = (EditText) findViewById(R.id.product_create_unit);
        text_class = (EditText) findViewById(R.id.product_create_class);
        text_intro = (EditText) findViewById(R.id.product_create_intro);
        confirm = (Button) findViewById(R.id.product_create_confirm);
    }

    private static class MHandler extends Handler {
        private final WeakReference<CreateProduct> outer;
        MHandler(CreateProduct target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            CreateProduct target = outer.get();
            if (target != null) {
                String str = (String) msg.obj;
                Toast.makeText(target, str, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
