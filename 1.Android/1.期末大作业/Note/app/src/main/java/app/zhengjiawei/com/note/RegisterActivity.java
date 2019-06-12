package app.zhengjiawei.com.note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText editText1, editText2;
    private ImageButton button1, button2;
    private Button button3, button4;
    private ImageView imageView;
    private int i=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText1 = findViewById(R.id.editText3);
        editText2 = findViewById(R.id.editText4);
        button1 = findViewById(R.id.imageButton2); // 上一张
        button2 = findViewById(R.id.imageButton); // 下一张
        button3 = findViewById(R.id.button4);     // 取消
        button4 = findViewById(R.id.button3);    // 注册
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(getResources().getIdentifier("p"+ i,"drawable","app.zhengjiawei.com.note"));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i>9){
                    i=1;
                }
                int id = getResources().getIdentifier("p"+i,"drawable","app.zhengjiawei.com.note");
                imageView.setImageResource(id);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if (i==0){
                    i = 9;
                }
                int id=getResources().getIdentifier("p"+i,"drawable","app.zhengjiawei.com.note");
                imageView.setImageResource(id);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.getText().toString().isEmpty()){
                    editText1.setError("用户账号不能为空！");
                    return;
                }
                if (editText2.getText().toString().isEmpty()){
                    editText2.setError("用户密码不能为空！");
                    return;
                }

                String account = editText1.getText().toString();
                String password = editText2.getText().toString();
                
                ContentValues values = new ContentValues();
                values.put("account",account);
                values.put("password",password);
                values.put("img",getResources().getIdentifier("p"+ i,"drawable","app.zhengjiawei.com.note"));

                MyOpenHelper myOpenHelper = new MyOpenHelper(RegisterActivity.this);
                SQLiteDatabase db = myOpenHelper.getReadableDatabase();

                Cursor cursor = db.query("user",null,"account=?",new String[]{account},null,null,null);
                if (cursor.moveToFirst()==true || account.equals("root")){
                    Toast.makeText(RegisterActivity.this,"账号已存在！",Toast.LENGTH_SHORT).show();
                }else {
                    db.insert("user",null,values);
                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    if (db != null && db.isOpen()){
                        db.close();
                    }
                    finish();
                }
            }
        });
    }
}
