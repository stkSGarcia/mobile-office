package stk.mobileoffice.product;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONObject;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductDetail extends AppCompatActivity {
    private String id;
    private MHandler handler = new MHandler(this);
    private TextView text_name;
    private TextView text_number;
    private TextView text_price;
    private TextView text_unit;
    private TextView text_class;
    private TextView text_intro;
    private TextView text_remark;
    private ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
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
        text_number = (TextView) findViewById(R.id.product_detail_number);
        text_price = (TextView) findViewById(R.id.product_detail_price);
        text_unit = (TextView) findViewById(R.id.product_detail_unit);
        text_class = (TextView) findViewById(R.id.product_detail_class);
        text_intro = (TextView) findViewById(R.id.product_detail_intro);
        text_remark = (TextView) findViewById(R.id.product_detail_remark);
        pic = (ImageView) findViewById(R.id.product_detail_pic);
    }

    private void showDetail() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/product_query_json?productid=" + id);
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
                        Log.i("Product_Detail_Data", result.toString());
                        JSONObject single = new JSONObject(result.toString()).getJSONObject("0");
                        Message msg = new Message();
                        String str = single.getString("productname") + ";" +
                                single.getString("productsn") + ";" +
                                single.getString("standardprice") + ";" +
                                single.getString("salesunit") + ";" +
                                single.getString("classification") + ";" +
                                single.getString("introduction") + ";" +
                                single.getString("productremarks");
                        Bitmap bmp = null;
                        try {
                            URL bmpUrl = new URL(single.getString("picture").replace("\\", ""));
                            HttpURLConnection connection = (HttpURLConnection) bmpUrl.openConnection();
                            connection.setDoInput(true);
                            connection.setUseCaches(false);
                            connection.connect();
                            InputStream inputStream = connection.getInputStream();
                            bmp = BitmapFactory.decodeStream(inputStream);
                        } catch (Exception e1) {
                            Log.e("Product_Detail", "Get picture failed.");
                        }
                        msg.obj = new Object[]{str, bmp};
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Log.e("Product_Detail", "Get detail failed.");
                }
            }
        }).start();
    }

    private class MHandler extends Handler {
        private final WeakReference<ProductDetail> outer;
        MHandler(ProductDetail target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            ProductDetail target = outer.get();
            if (target != null) {
                Object[] raw = (Object[]) msg.obj;
                String[] data = ((String) raw[0]).split(";", -1);
                getSupportActionBar().setTitle(data[0]);
                text_name.setText(data[0]);
                text_number.setText(data[1]);
                text_price.setText(data[2]);
                text_unit.setText(data[3]);
                text_class.setText(data[4]);
                text_intro.setText(data[5]);
                text_remark.setText(data[6]);
                Bitmap bmp = (Bitmap) raw[1];
                if (bmp != null)
                    pic.setImageBitmap(bmp);
            }
        }
    }
}
