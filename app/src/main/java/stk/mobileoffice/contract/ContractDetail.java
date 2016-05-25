package stk.mobileoffice.contract;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import stk.mobileoffice.DBHelper;
import stk.mobileoffice.R;

public class ContractDetail extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contract_detail, container, false);
        TextView text_name = (TextView) view.findViewById(R.id.contract_detail_name);
        TextView text_level = (TextView) view.findViewById(R.id.contract_detail_level);
        TextView text_price = (TextView) view.findViewById(R.id.contract_detail_price);
        TextView text_a = (TextView) view.findViewById(R.id.contract_detail_a);
        TextView text_b = (TextView) view.findViewById(R.id.contract_detail_b);
        TextView text_desc = (TextView) view.findViewById(R.id.contract_detail_desc);
        text_desc.setMovementMethod(ScrollingMovementMethod.getInstance());

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "contract", null, "_id = ?", new String[]{getArguments().getString("_id")}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            text_name.setText("NULL");
        } else {
            text_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            text_level.setText(cursor.getString(cursor.getColumnIndex("level")));
            text_price.setText(cursor.getString(cursor.getColumnIndex("price")) + " 元");
            text_a.setText(cursor.getString(cursor.getColumnIndex("a")));
            text_b.setText(cursor.getString(cursor.getColumnIndex("b")));
            text_desc.setText(cursor.getString(cursor.getColumnIndex("desc")));
        }
        cursor.close();
        db.close();
        return view;
    }
}
