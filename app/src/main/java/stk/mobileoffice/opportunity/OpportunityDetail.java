package stk.mobileoffice.opportunity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import stk.mobileoffice.DBHelper;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpportunityDetail extends Fragment {
    private TextView text_name;
    private TextView text_level;
    private TextView text_desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opportunity_detail, container, false);
        text_name = (TextView) view.findViewById(R.id.opportunity_detail_name);
        text_level = (TextView) view.findViewById(R.id.opportunity_detail_level);
        text_desc = (TextView) view.findViewById(R.id.opportunity_detail_desc);
        text_desc.setMovementMethod(ScrollingMovementMethod.getInstance());
//        showDemoDetail(getArguments().getString("_id"));
        showDetail(getArguments().getString("_id"));
        return view;
    }

    private void showDetail(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/opportunity_query_json");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("opportunityid=");
                    stringBuilder.append(id);
                    byte[] data = stringBuilder.toString().getBytes("UTF-8");
                    OutputStream out = con.getOutputStream();
                    out.write(data);
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder result = new StringBuilder();
                        String s;
                        while ((s = in.readLine()) != null)
                            result.append(s);
                        Log.d("qqqqqqqqqq", result.toString());
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
        Cursor cursor = db.query(true, "opportunity", null, "_id = ?", new String[]{id}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            text_name.setText("NULL");
        } else {
            text_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            text_level.setText(cursor.getString(cursor.getColumnIndex("level")));
            text_desc.setText(cursor.getString(cursor.getColumnIndex("desc")));
        }
        cursor.close();
        db.close();
    }
}
