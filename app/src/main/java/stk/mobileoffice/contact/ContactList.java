package stk.mobileoffice.contact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SimpleAdapter;
import org.json.JSONObject;
import stk.mobileoffice.ContentList;
import stk.mobileoffice.CurrentUser;
import stk.mobileoffice.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ContactList extends ContentList {
	@Override
	protected void showDetail(Map<String, Object> i) {
		Intent intent = new Intent(this.getActivity(), ContactDetail.class);
		intent.putExtra("_id", i.get("_id")+"");
		startActivity(intent);
	}

	@Override
	protected void set() {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("联系人");
		adapter = new SimpleAdapter(getContext(), data, R.layout.contact_list, new String[]{"name", "image"}, new int[]{R.id.contact_list_name, R.id.contact_list_image});
		leftButton.setText("全部联系人");
		rightButton.setText("我的联系人");
		runnable = new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/common_contacts_json");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					con.setDoOutput(true);
					con.setDoInput(true);
					if (isChanged)
						dataClear();
					currentpage++;
					String str;
					if (mode == 0)
						str = "currentpage=" + currentpage;
					else
						str = "currentpage=" + currentpage + "&staffid=" + CurrentUser.id;
					byte[] strData = str.getBytes("UTF-8");
					OutputStream out = con.getOutputStream();
					out.write(strData);
					if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						StringBuilder result = new StringBuilder();
						String s;
						while ((s = in.readLine()) != null)
							result.append(s);
						Log.i("Contact_List_Data", result.toString());
						JSONObject total = new JSONObject(result.toString());
						pagecount = total.getInt("pagecount");
						JSONObject single;
						Map<String, Object> map;
						for (int i = 0; i < total.getInt("currentcount"); i++) {
							map = new HashMap<>();
							single = total.getJSONObject(i+"");
							map.put("_id", single.getInt("contactsid"));
							map.put("name", single.getString("contactsname"));
							map.put("image", R.drawable.ic_menu_contact);
							data.add(map);
						}
						handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					Log.e("Contact_List", "Get detail failed.");
				}
			}
		};
	}
}
