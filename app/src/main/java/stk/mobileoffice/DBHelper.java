package stk.mobileoffice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "office.db";
    private final static int version = 1;
    private final static String CREATE_OPPORTUNITY = "create table opportunity(_id integer primary key autoincrement, name varchar(100), level varchar(100), desc text)";
    private final static String CREATE_CUSTOMER = "create table customer(_id integer primary key autoincrement, name varchar(100), level varchar(100))";
    private final static String CREATE_CONTRACT = "create table contract(_id integer primary key autoincrement, name varchar(100), level varchar(100), price double, a varchar(100), b varchar(100), desc text)";
    private final static String CREATE_PRODUCT = "create table product(_id integer primary key autoincrement, name varchar(100), number varchar(100), price double, unit varchar(100), desc text)";
    private final static String CREATE_CONTACT = "create table contact(_id integer primary key autoincrement, name varchar(100), tel varchar(100), desc text)";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_OPPORTUNITY);
        sqLiteDatabase.execSQL(CREATE_CUSTOMER);
        sqLiteDatabase.execSQL(CREATE_CONTRACT);
        sqLiteDatabase.execSQL(CREATE_PRODUCT);
        sqLiteDatabase.execSQL(CREATE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("Upgrade Database");
    }
}
