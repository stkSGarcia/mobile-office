package stk.mobileoffice;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DemoData {
    public static void add_opportunity(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("drop table if exists opportunity");
        db.execSQL("create table opportunity(_id integer primary key autoincrement, name varchar(100), level varchar(50), desc text)");
        String[] name = {"东京", "南京", "西京", "北京", "清华", "剑桥", "哈佛", "中国科技", "上海交通", "早稻田"};
        String[] level = {"普通", "重要", "重要", "重要", "普通", "普通", "重要", "普通", "重要", "重要"};
        ContentValues values;
        for (int i = 0; i < 10; i++) {
            values = new ContentValues();
            values.put("name", name[i] + "大学");
            values.put("level", level[i] + "商机");
            values.put("desc", "来自" + name[i] + "大学的" + level[i] + "商机");
            db.insert("opportunity", "name", values);
        }
        db.close();
    }
}
