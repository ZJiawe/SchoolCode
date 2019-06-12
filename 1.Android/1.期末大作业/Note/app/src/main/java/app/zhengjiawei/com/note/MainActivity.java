package app.zhengjiawei.com.note;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2;
    private EditText editText1, editText2;
    private String accStr = "";
    private String passStr = "";

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        editText1 = findViewById(R.id.gg);
        editText2 = findViewById(R.id.cc);

        myOpenHelper = new MyOpenHelper(MainActivity.this);
        db = myOpenHelper.getReadableDatabase();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accStr = editText1.getText().toString();
                passStr = editText2.getText().toString();
                Cursor cursor = db.query("user",null,"account=? and password=?",new String[]{accStr,passStr},null,null,null);
               // 管理员账号 直接进入管理员界面
                if (passStr.equals("root") && accStr.equals("root")) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                } else {
                    if (cursor.moveToFirst()) {
                        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                        intent.putExtra("user_id", cursor.getInt(cursor.getColumnIndex("_id")));  // 传送账号ID
                        intent.putExtra("img", cursor.getInt(cursor.getColumnIndex("img")));  // 传送账号ID
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this,"账号密码不正确！登陆失败！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
