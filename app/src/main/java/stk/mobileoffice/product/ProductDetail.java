package stk.mobileoffice.product;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONObject;
import stk.mobileoffice.DBHelper;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ProductDetail extends Fragment {
    private MHandler handler = new MHandler(this);
    private TextView text_name;
    private TextView text_number;
    private TextView text_price;
    private TextView text_unit;
    private TextView text_desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail, container, false);
        text_name = (TextView) view.findViewById(R.id.product_detail_name);
        text_number = (TextView) view.findViewById(R.id.product_detail_number);
        text_price = (TextView) view.findViewById(R.id.product_detail_price);
        text_unit = (TextView) view.findViewById(R.id.product_detail_unit);
        text_desc = (TextView) view.findViewById(R.id.product_detail_desc);
//        showDemoDetail(getArguments().getString("_id"));
        showDetail(getArguments().getString("_id"));
        return view;
    }

    private void showDetail(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder stringBuilder = new StringBuilder("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/product_query_json?");
                    stringBuilder.append("productid=");
                    stringBuilder.append(id);
                    URL url = new URL(stringBuilder.toString());
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
                        JSONObject single = new JSONObject(result.toString()).getJSONObject("0");
                        Message msg = new Message();
                        String str = single.getString("productname") + ";" +
                                single.getString("productsn") + ";" +
                                single.getString("standardprice") + ";" +
                                single.getString("salesunit") + ";" +
                                single.getString("introduction");
                        msg.obj = str;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showDemoDetail(String id) {
        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "product", null, "_id = ?", new String[]{id}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            text_name.setText("NULL");
        } else {
            text_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            text_number.setText(cursor.getString(cursor.getColumnIndex("number")));
            text_price.setText(cursor.getString(cursor.getColumnIndex("price")));
            text_unit.setText(cursor.getString(cursor.getColumnIndex("unit")));
            text_desc.setText(cursor.getString(cursor.getColumnIndex("desc")));
        }
        cursor.close();
        db.close();
    }

    private class MHandler extends Handler {
        private final WeakReference<ProductDetail> outer;
        public MHandler(ProductDetail target) {
            outer = new WeakReference<>(target);
        }
        @Override
        public void handleMessage(Message msg) {
            ProductDetail target = outer.get();
            if (target != null) {
                String raw = (String) msg.obj;
                String[] data = raw.split(";");
                text_name.setText(data[0]);
                text_number.setText(data[1]);
                text_price.setText(data[2]);
                text_unit.setText(data[3]);
                text_desc.setText(data[4]);
            }
        }
    }
}
