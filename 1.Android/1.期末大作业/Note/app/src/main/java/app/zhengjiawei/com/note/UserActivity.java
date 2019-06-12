package app.zhengjiawei.com.note;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private int _id;

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;

    String account = "";
    Integer img = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listView=findViewById(R.id.lv);
        searchView=findViewById(R.id.searchView);

        myOpenHelper=new MyOpenHelper(UserActivity.this);
        db = myOpenHelper.getReadableDatabase();

        Cursor cursor = db.query("user", null, null, null, null, null, null);

        adapter = new SimpleCursorAdapter(UserActivity.this,R.layout.userlayout,cursor,
                new String[]{"account","img"},
                new int[]{R.id.textView19, R.id.imageView2},0
        );

        listView.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = db.query("user",null,"account like ? ",new String[]{"%"+newText+"%"},null,null,null);
                adapter.changeCursor(cursor);
                return true;
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = adapter.getCursor();
                c.moveToPosition(position);
                _id = c.getInt(c.getColumnIndex("_id"));
                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setTitle("删除用户");
                builder.setMessage("确定删除用户？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("user","_id=?", new String[]{_id + ""});
                        db.delete("note", "user_id=?", new String[]{_id + ""});
                        Cursor cursor = db.query("user", null, null, null, null, null, null);
                        adapter.changeCursor(cursor);
                        Toast.makeText(UserActivity.this,"删除用户成功！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = adapter.getCursor();
                c.moveToPosition(position);

                _id = c.getInt(c.getColumnIndex("_id"));
                account = c.getString(c.getColumnIndex("account"));
                img = c.getInt(c.getColumnIndex("img"));

                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                builder.setTitle("重置密码");
                builder.setMessage("确定重置密码？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ContentValues values = new ContentValues();
                        values.put("_id",_id);
                        values.put("account",account);
                        values.put("password","000000");  // 重置的密码
                        values.put("img",img);

                        db.update("user", values, "_id=?", new String[]{_id + ""});
                        Cursor cursor = db.query("user", null, null, null, null, null, null);
                        adapter.changeCursor(cursor);
                        Toast.makeText(UserActivity.this,"重置密码成功！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        adapter.changeCursor(cursor);
    }
}
