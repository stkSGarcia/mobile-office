package stk.mobileoffice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "office.db";
    private final static int version = 1;
    private final static String CREATE_OPPORTUNITY = "create table opportunity(_id integer primary key autoincrement, name varchar(100), level varchar(50), desc text)";
    private final static String CREATE_CUSTOMER = "";
    private final static String CREATE_CONTRACT = "";
    private final static String CREATE_BUSINESS = "";
    private final static String CREATE_PRODUCT = "";
    private final static String CREATE_CONTACT = "create table contact(_id integer primary key autoincrement, name varchar(100), )";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_OPPORTUNITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("Upgrade Database");
    }
}
