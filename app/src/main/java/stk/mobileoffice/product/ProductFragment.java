package stk.mobileoffice.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import stk.mobileoffice.R;

public class ProductFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("产品");
		View view = inflater.inflate(R.layout.product_content, container, false);
		return view;
	}
}
