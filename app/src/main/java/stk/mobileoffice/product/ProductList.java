package stk.mobileoffice.product;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.json.JSONObject;
import stk.mobileoffice.DBHelper;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductList extends Fragment {
	private List<Map<String, Object>> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("产品");
		View view = inflater.inflate(R.layout.content_list, container, false);
//		data = getDemoData();
		data = getData();
		SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.product_list, new String[]{"name", "price", "image"}, new int[]{R.id.product_list_name, R.id.product_list_price, R.id.product_list_image});
		ListView list = (ListView) view.findViewById(R.id.content_list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				showDetail((int) data.get(i).get("_id"));
			}
		});
		return view;
	}

	private List<Map<String, Object>> getData() {
		final List<Map<String, Object>> list = new ArrayList<>();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/common_product_json");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					con.setDoOutput(true);
					con.setDoInput(true);
					String str = "currentpage=1&search=";
					byte[] data = str.getBytes("UTF-8");
					OutputStream out = con.getOutputStream();
					out.write(data);
					if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						StringBuilder result = new StringBuilder();
						String s;
						while ((s = in.readLine()) != null)
							result.append(s);
						JSONObject total = new JSONObject(result.toString());
						JSONObject single;
						Map<String, Object> map;
						for (int i = 0; i < total.getInt("currentcount"); i++) {
							map = new HashMap<>();
							single = total.getJSONObject(i+"");
							map.put("_id", single.getInt("productid"));
							map.put("name", single.getString("productname"));
							map.put("price", single.getString("standardprice") + " 元");
							map.put("image", R.drawable.ic_menu_product);
							list.add(map);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}

	private List<Map<String, Object>> getDemoData() {
		DBHelper dbHelper = new DBHelper(getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(true, "product", new String[]{"_id", "name", "price"}, null, null, null, null, null, null);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		while (cursor.moveToNext()) {
			map = new HashMap<>();
			map.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("price", cursor.getString(cursor.getColumnIndex("price")) + " 元");
			map.put("image", R.drawable.ic_menu_product);
			list.add(map);
		}
		cursor.close();
		db.close();
		return list;
	}

	private void showDetail(int id) {
		Fragment fragment = new ProductDetail();
		Bundle bundle = new Bundle();
		bundle.putString("_id", id+"");
		fragment.setArguments(bundle);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}
