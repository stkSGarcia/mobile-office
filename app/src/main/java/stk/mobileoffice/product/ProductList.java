package stk.mobileoffice.product;

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

public class ProductList extends ContentList {
	@Override
	protected void set() {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("产品");
		adapter = new SimpleAdapter(getContext(), data, R.layout.product_list, new String[]{"name", "price", "image"}, new int[]{R.id.product_list_name, R.id.product_list_price, R.id.product_list_image});
	}

	@Override
	protected void showDetail(int id) {
		Intent intent = new Intent(this.getActivity(), ProductDetail.class);
		intent.putExtra("_id", id+"");
		startActivity(intent);
	}

	@Override
	protected void showData() {
		currentpage++;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/common_product_json");
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
						Log.i("Product_List_Data", result.toString());
						JSONObject total = new JSONObject(result.toString());
						pagecount = total.getInt("pagecount");
						JSONObject single;
						Map<String, Object> map;
						for (int i = 0; i < total.getInt("currentcount"); i++) {
							map = new HashMap<>();
							single = total.getJSONObject(i+"");
							map.put("_id", single.getInt("productid"));
							map.put("name", single.getString("productname"));
							map.put("price", single.getString("standardprice") + " 元");
							map.put("image", R.drawable.ic_menu_product);
							data.add(map);
						}
					}
				} catch (Exception e) {
					Log.e("Product_List", "Get detail failed.");
				}
			}
		}).start();
	}
}
