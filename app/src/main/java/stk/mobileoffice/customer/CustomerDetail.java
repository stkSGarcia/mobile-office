package stk.mobileoffice.customer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import stk.mobileoffice.DBHelper;
import stk.mobileoffice.R;

public class CustomerDetail extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_detail, container, false);
        TextView text_name = (TextView) view.findViewById(R.id.customer_detail_name);
        TextView text_level = (TextView) view.findViewById(R.id.customer_detail_level);

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "customer", null, "_id = ?", new String[]{getArguments().getString("_id")}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            text_name.setText("NULL");
        } else {
            text_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            text_level.setText(cursor.getString(cursor.getColumnIndex("level")));
        }
        cursor.close();
        db.close();
        return view;
    }
}
