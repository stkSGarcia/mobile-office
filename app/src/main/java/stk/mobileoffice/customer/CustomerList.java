package stk.mobileoffice.customer;

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
import stk.mobileoffice.DBHelper;
import stk.mobileoffice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerList extends Fragment {
	private List<Map<String, Object>> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("客户");
		View view = inflater.inflate(R.layout.content_list, container, false);
		data = getData();
		SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.customer_list, new String[]{"name", "level", "image"}, new int[]{R.id.customer_list_name, R.id.customer_list_level, R.id.customer_list_image});
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
		DBHelper dbHelper = new DBHelper(getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(true, "customer", new String[]{"_id", "name", "level"}, null, null, null, null, null, null);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		while (cursor.moveToNext()) {
			map = new HashMap<>();
			map.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("level", cursor.getString(cursor.getColumnIndex("level")));
			map.put("image", R.drawable.ic_menu_customer);
			list.add(map);
		}
		cursor.close();
		db.close();
		return list;
	}

	private void showDetail(int id) {
		Fragment fragment = new CustomerDetail();
		Bundle bundle = new Bundle();
		bundle.putString("_id", id+"");
		fragment.setArguments(bundle);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}