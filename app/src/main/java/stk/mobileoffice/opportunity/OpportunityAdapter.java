package stk.mobileoffice.opportunity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import stk.mobileoffice.R;
import stk.mobileoffice.opportunity.Opportunity;

import java.util.LinkedList;

public class OpportunityAdapter extends BaseAdapter {
	private Context context;
	private LinkedList<Opportunity> data;

	public OpportunityAdapter(Context context, LinkedList<Opportunity> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.opportunity_list, parent, false);
		TextView name = (TextView) convertView.findViewById(R.id.opportunity_list_name);
		TextView description = (TextView) convertView.findViewById(R.id.opportunity_list_description);
		name.setText(data.get(position).getName());
		description.setText(data.get(position).getDescription());
		return convertView;
	}
}
