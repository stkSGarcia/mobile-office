package stk.mobileoffice.contact;

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

public class ContactDetail extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_detail, container, false);
        TextView text_name = (TextView) view.findViewById(R.id.contact_detail_name);
        TextView text_tel = (TextView) view.findViewById(R.id.contact_detail_tel);
        TextView text_desc = (TextView) view.findViewById(R.id.contact_detail_desc);

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "contact", null, "_id = ?", new String[]{getArguments().getString("_id")}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            text_name.setText("NULL");
        } else {
            text_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            text_tel.setText(cursor.getString(cursor.getColumnIndex("tel")));
            text_desc.setText(cursor.getString(cursor.getColumnIndex("desc")));
        }
        cursor.close();
        db.close();
        return view;
    }
}
