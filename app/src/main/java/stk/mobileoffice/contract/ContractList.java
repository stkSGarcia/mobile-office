package stk.mobileoffice.contract;

import android.support.v7.app.AppCompatActivity;
import android.widget.SimpleAdapter;
import stk.mobileoffice.ContentList;
import stk.mobileoffice.R;

import java.util.Map;


public class ContractList extends ContentList {
	@Override
	protected void set() {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("合同");
		adapter = new SimpleAdapter(getContext(), data, R.layout.contract_list, new String[]{"name", "level", "image"}, new int[]{R.id.contract_list_name, R.id.contract_list_level, R.id.contract_list_image});
	}

	@Override
	protected void showDetail(Map<String, Object> i) {
	}

	@Override
	protected void showData() {
	}
}
