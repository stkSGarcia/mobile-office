package stk.mobileoffice.product;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import stk.mobileoffice.DBHelper;
import stk.mobileoffice.R;

public class ProductDetail extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail, container, false);
        TextView text_name = (TextView) view.findViewById(R.id.product_detail_name);
        TextView text_number = (TextView) view.findViewById(R.id.product_detail_number);
        TextView text_price = (TextView) view.findViewById(R.id.product_detail_price);
        TextView text_unit = (TextView) view.findViewById(R.id.product_detail_unit);
        TextView text_desc = (TextView) view.findViewById(R.id.product_detail_desc);

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(true, "product", null, "_id = ?", new String[]{getArguments().getString("_id")}, null, null, null, null);
        if (!cursor.moveToFirst()) {
            text_name.setText("NULL");
        } else {
            text_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            text_number.setText(cursor.getString(cursor.getColumnIndex("number")));
            text_price.setText(cursor.getString(cursor.getColumnIndex("price")));
            text_unit.setText(cursor.getString(cursor.getColumnIndex("unit")));
            text_desc.setText(cursor.getString(cursor.getColumnIndex("desc")));
        }
        cursor.close();
        db.close();
        return view;
    }
}
