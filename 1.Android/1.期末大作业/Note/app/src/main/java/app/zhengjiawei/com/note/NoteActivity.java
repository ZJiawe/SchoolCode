package app.zhengjiawei.com.note;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private ImageButton button;
    private SearchView searchView;
    private ListView listView;
    private ImageView imageView;

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private int user_id;
    private int _id;
    private int img;
    private String account = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        button=findViewById(R.id.imageButton4);
        searchView=findViewById(R.id.searchView);
        listView=findViewById(R.id.lv);
        imageView=findViewById(R.id.imageView2);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",0);
        img = intent.getIntExtra("img",0);
        account = intent.getStringExtra("account");
        password = intent.getStringExtra("password");
        imageView.setImageResource(img);

        myOpenHelper=new MyOpenHelper(NoteActivity.this);
        db = myOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("note", null, "user_id=?", new String[]{user_id + ""}, null, null, null);
        adapter = new SimpleCursorAdapter(NoteActivity.this,R.layout.listitem_layout,cursor,
                new String[]{"title","content","date"},
                new int[]{R.id.textView10,R.id.textView11,R.id.textView12},0
        );

        listView.setAdapter(adapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                builder.setTitle("注销");
                builder.setMessage("确定注销？返回登陆界面？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(NoteActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this,InsertNoteActivity.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = db.query("note",null,"title like ? and user_id=?",new String[]{"%"+newText+"%", user_id + ""},null,null,null);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(NoteActivity.this);
                builder.setTitle("删除便签");
                builder.setMessage("确定删除便签？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("note","_id=?", new String[]{_id + ""});
                        Cursor cursor = db.query("note", null, "user_id=?", new String[]{user_id + ""}, null, null, null);
                        adapter.changeCursor(cursor);
                        Toast.makeText(NoteActivity.this,"删除便签成功！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteActivity.this,EditNoteActivity.class);
                Cursor c = adapter.getCursor();
                c.moveToPosition(position);
                int _id = c.getInt(c.getColumnIndex("_id"));
                String title = c.getString(c.getColumnIndex("title"));
                String date = c.getString(c.getColumnIndex("date"));
                String content = c.getString(c.getColumnIndex("content"));
                intent.putExtra("_id", _id);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("date", date);
                intent.putExtra("user_id", user_id);    // 给user_id
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = db.query("note", null, "user_id=?", new String[]{user_id + ""}, null, null, null);
        adapter.changeCursor(cursor);
    }

}
