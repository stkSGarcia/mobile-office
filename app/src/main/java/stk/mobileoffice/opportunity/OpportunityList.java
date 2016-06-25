package stk.mobileoffice.opportunity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SimpleAdapter;
import org.json.JSONObject;
import stk.mobileoffice.ContentList;
import stk.mobileoffice.CurrentUser;
import stk.mobileoffice.R;
import stk.mobileoffice.TypeMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OpportunityList extends ContentList {
	@Override
	protected void showDetail(Map<String, Object> i) {
		Intent intent = new Intent(this.getActivity(), OpportunityDetail.class);
		String msg = i.get("_id") +";"+ i.get("customername");
		intent.putExtra("msg", msg);
		startActivity(intent);
	}

	@Override
	protected void set() {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("商机");
		adapter = new SimpleAdapter(getContext(), data, R.layout.opportunity_list, new String[]{"name", "level", "image"}, new int[]{R.id.opportunity_list_name, R.id.opportunity_list_level, R.id.opportunity_list_image});
		leftButton.setText("全部商机");
		rightButton.setText("我的商机");
		runnable = new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL("http://nqiwx.mooctest.net:8090/wexin.php/Api/Index/common_opportunity_json");
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
						Log.i("Opportunity_List_Data", result.toString());
						JSONObject total = new JSONObject(result.toString());
						pagecount = total.getInt("pagecount");
						JSONObject single;
						Map<String, Object> map;
						for (int i = 0; i < total.getInt("currentcount"); i++) {
							map = new HashMap<>();
							single = total.getJSONObject(i+"");
							map.put("_id", single.getInt("opportunityid"));
							map.put("customername", single.getString("customername"));
							map.put("name", single.getString("opportunitytitle"));
							map.put("level", TypeMap.getBusniessType(single.getString("businesstype")));
							map.put("image", R.drawable.ic_menu_opportunity);
							data.add(map);
						}
						handler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					Log.e("Opportunity_List", "Get detail failed.");
				}
			}
		};
	}
}
