package stk.mobileoffice;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DemoData {
    public static void add_opportunity(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("drop table if exists opportunity");
        db.execSQL("create table opportunity(_id integer primary key autoincrement, name varchar(100), level varchar(100), desc text)");
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

    public static void add_customer(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("drop table if exists customer");
        db.execSQL("create table customer(_id integer primary key autoincrement, name varchar(100), level varchar(100))");
        String[] name = {"小明", "小红", "小刚", "小芳", "买买提", "阿凡达", "大雄", "多啦A梦", "静香", "胖虎"};
        String[] level = {"普通", "重要", "重要", "重要", "普通", "普通", "重要", "普通", "重要", "重要"};
        ContentValues values;
        for (int i = 0; i < 10; i++) {
            values = new ContentValues();
            values.put("name", name[i]);
            values.put("level", level[i] + "客户");
            db.insert("customer", "name", values);
        }
        db.close();
    }

    public static void add_contract(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("drop table if exists contract");
        db.execSQL("create table contract(_id integer primary key autoincrement, name varchar(100), level varchar(100), price double, a varchar(100), b varchar(100), desc text)");
        String[] name = {"苹果一万箱", "香蕉一千箱", "菠萝蜜一百箱", "榴莲五十箱", "橘子三十箱", "RPG十箱"};
        double[] price = {4000.0, 2000.0, 1500.34, 1000.00, 550, 2000000.00};
        String[] a = {"多啦A梦", "尼古拉斯", "小刚", "尼古拉斯", "宋中鸡", "阿凡达"};
        String[] b = {"沃兹基硕德", "小刚", "沃兹基硕德", "阿凡达", "多啦A梦", "宋中鸡"};
        ContentValues values;
        for (int i = 0; i < 6; i++) {
            values = new ContentValues();
            values.put("name", name[i]);
            values.put("level", "直销合同");
            values.put("price", price[i]);
            values.put("a", a[i]);
            values.put("b", b[i]);
            values.put("desc", "这是" + a[i] + "和" + b[i] + "签订的合同");
            db.insert("contract", "name", values);
        }
        db.close();
    }

    public static void add_product(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("drop table if exists product");
        db.execSQL("create table product(_id integer primary key autoincrement, name varchar(100), number varchar(100), price double, unit varchar(100), desc text)");
        String[] name = {"喷墨打印机", "复印机", "扫描仪", "订书机", "激光打印机", "胶水"};
        String[] number = {"VXT-40324", "ACC-af324", "MM-789", "ATPX-732", "123005", "TOEIFJ"};
        double[] price = {1000.0, 4500.5, 100000.0, 25.5, 2000.0, 10.78};
        String[] unit = {"台", "台", "台", "个", "台", "瓶"};
        ContentValues values;
        for (int i = 0; i < 6; i++) {
            values = new ContentValues();
            values.put("name", name[i]);
            values.put("number", number[i]);
            values.put("price", price[i]);
            values.put("unit", unit[i]);
            values.put("desc", "这是一" + unit[i] + name[i]);
            db.insert("product", "name", values);
        }
        db.close();
    }

    public static void add_contact(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("drop table if exists contact");
        db.execSQL("create table contact(_id integer primary key autoincrement, name varchar(100), tel varchar(100), desc text)");
        String[] name = {"宋中鸡", "宋中鸭", "宋中鹅", "张三", "李四", "赵四", "尼古拉斯", "沃兹基硕德", "A", "B"};
        String[] tel = {"11111111", "12121212", "23333333", "", "78787878", "66666666", "", "55555555", "", "99999999"};
        ContentValues values;
        for (int i = 0; i < 10; i++) {
            values = new ContentValues();
            values.put("name", name[i]);
            values.put("tel", tel[i]);
            values.put("desc", "QQ: " + i * 154323);
            db.insert("contact", "name", values);
        }
        db.close();
    }
}
