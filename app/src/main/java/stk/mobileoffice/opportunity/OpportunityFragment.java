package stk.mobileoffice.opportunity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import stk.mobileoffice.R;

import java.util.LinkedList;

public class OpportunityFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.opportunity_content, container, false);

		LinkedList<Opportunity> data = new LinkedList<>();
		data.add(new Opportunity("abcd", "1234"));
		data.add(new Opportunity("efgh", "5678"));
		data.add(new Opportunity("ijkl", "1357"));
		Context context = getContext();
		OpportunityAdapter opportunityAdapter = new OpportunityAdapter(context, data);

		ListView list = (ListView) view.findViewById(R.id.opportunity_list);
		list.setAdapter(opportunityAdapter);
		return view;
	}
}
