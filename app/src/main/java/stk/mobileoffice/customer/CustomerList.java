package stk.mobileoffice.customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SimpleAdapter;
import org.json.JSONObject;
import stk.mobileoffice.ContentList;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CustomerList extends ContentList {
	@Override
	protected void set() {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("客户");
		adapter = new SimpleAdapter(getContext(), data, R.layout.customer_list, new String[]{"name", "level", "image"}, new int[]{R.id.customer_list_name, R.id.customer_list_level, R.id.customer_list_image});
	}

	@Override
	protected void showData() {
		currentpage++;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/common_customer_json");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					con.setDoOutput(true);
					con.setDoInput(true);
					String str = "currentpage=" + currentpage;
					byte[] strData = str.getBytes("UTF-8");
					OutputStream out = con.getOutputStream();
					out.write(strData);
					if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						StringBuilder result = new StringBuilder();
						String s;
						while ((s = in.readLine()) != null)
							result.append(s);
						Log.i("Customer_List_Data", result.toString());
						JSONObject total = new JSONObject(result.toString());
						pagecount = total.getInt("pagecount");
						JSONObject single;
						Map<String, Object> map;
						for (int i = 0; i < total.getInt("currentcount"); i++) {
							map = new HashMap<>();
							single = total.getJSONObject(i+"");
							map.put("_id", single.getInt("customerid"));
							map.put("name", single.getString("customername"));
							map.put("level", single.getString("customertype"));
							map.put("image", R.drawable.ic_menu_customer);
							data.add(map);
						}
					}
				} catch (Exception e) {
					Log.e("Customer_List", "Get detail failed.");
				}
			}
		}).start();
	}

	@Override
	protected void showDetail(int id) {
		Intent intent = new Intent(this.getActivity(), CustomerDetail.class);
		intent.putExtra("_id", id+"");
		startActivity(intent);
	}
}
