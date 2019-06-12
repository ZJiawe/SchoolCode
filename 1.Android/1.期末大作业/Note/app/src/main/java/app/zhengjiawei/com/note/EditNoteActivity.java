package app.zhengjiawei.com.note;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    private EditText editText1, editText2, editText3;
    private Button button1, button2;
    Integer user_id;
    Integer _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        button1 = findViewById(R.id.button5);    // 确认
        button2 = findViewById(R.id.button6);    // 取消
        editText1 = findViewById(R.id.editText6);    // 标题
        editText2 = findViewById(R.id.editText5);   // 日期
        editText3 = findViewById(R.id.editText8);  // 内容

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",0);
        _id = intent.getIntExtra("_id",0);

        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");

        editText1.setText(title);
        editText2.setText(date);
        editText3.setText(content);

        //  默认当前日期
        SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String   str   =   formatter.format(curDate);
        editText2.setText(str);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText1.getText().toString().isEmpty()){
                    editText1.setError("标题不能为空");
                    return;
                }
                if (editText2.getText().toString().isEmpty()){
                    editText2.setError("日期不能为空");
                    return;
                }
                if (editText3.getText().toString().isEmpty()){
                    editText3.setError("内容不能为空");
                    return;
                }

                String title = editText1.getText().toString();
                String date = editText2.getText().toString();
                String content = editText3.getText().toString();

                ContentValues values = new ContentValues();
                values.put("_id",_id);
                values.put("user_id",user_id);
                values.put("title",title);
                values.put("date",date);
                values.put("content",content);

                MyOpenHelper myOpenHelper = new MyOpenHelper(EditNoteActivity.this);
                SQLiteDatabase db = myOpenHelper.getReadableDatabase();

                db.update("note", values, "_id=? and user_id=?", new String[]{_id + "", user_id + ""});
                Toast.makeText(EditNoteActivity.this, "修改标签成功！", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
