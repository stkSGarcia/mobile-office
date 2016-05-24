package stk.mobileoffice.contact;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import stk.mobileoffice.DBHelper;
import stk.mobileoffice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactFragment extends Fragment {
	private List<Map<String, Object>> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("联系人");
		View view = inflater.inflate(R.layout.contact_content, container, false);
		return view;
	}

	private List<Map<String, Object>> getData() {
		DBHelper dbHelper = new DBHelper(getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(true, "contact", new String[]{"_id", "name", "level"}, null, null, null, null, null, null);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map;
		while (cursor.moveToNext()) {
			map = new HashMap<>();
			map.put("_id", cursor.getInt(cursor.getColumnIndex("_id")));
			map.put("name", cursor.getString(cursor.getColumnIndex("name")));
			map.put("", cursor.getString(cursor.getColumnIndex("level")));
			map.put("image", R.drawable.ic_menu_opportunity);
			list.add(map);
		}
		cursor.close();
		return list;
	}
}
