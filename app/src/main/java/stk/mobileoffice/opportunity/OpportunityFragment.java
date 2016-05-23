package stk.mobileoffice.opportunity;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import stk.mobileoffice.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpportunityFragment extends Fragment {
	private List<Map<String, Object>> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.opportunity_content, container, false);
		data = getData();
		SimpleAdapter adapter = new SimpleAdapter(getContext(), data, R.layout.opportunity_list, new String[]{"level", "name", "image"}, new int[]{R.id.opportunity_list_level, R.id.opportunity_list_name, R.id.opportunity_list_image});
		ListView list = (ListView) view.findViewById(R.id.opportunity_list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(getContext(), (String) data.get(i).get("name"), Toast.LENGTH_LONG).show();
			}
		});
		return view;
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<>();

		Map<String, Object> map = new HashMap<>();
		map.put("level", "普通商机");
		map.put("name", "南京大学");
		map.put("image", R.drawable.ic_menu_opportunity);
		list.add(map);

		map = new HashMap<>();
		map.put("level", "重要商机");
		map.put("name", "国务院");
		map.put("image", R.drawable.ic_menu_opportunity);
		list.add(map);

		for (int i = 0; i < 20; i++) {
			map = new HashMap<>();
			map.put("level", "重要商机");
			map.put("name", i + "");
			map.put("image", R.drawable.ic_menu_opportunity);
			list.add(map);
		}

		return list;
	}


}
