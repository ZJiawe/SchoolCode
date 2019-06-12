package app.zhengjiawei.com.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context,"NoteDB",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userSql = "create table user(" +
                "_id integer primary key autoincrement,"+
                "account text,"+
                "password text,"+
                "img integer)";


        String noteSql = "create table note(" +
                "_id integer primary key autoincrement,"+
                "user_id integer ,"+
                "title text,"+
                "date text,"+
                "content text)";

        db.execSQL(userSql);
        db.execSQL(noteSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
