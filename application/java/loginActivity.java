package com.example.grad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    //안드로이드 스튜디오에서 제공해주는 loginactivity
    //splashActivity에서 intent로 넘어오는 activity

    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;

    EditText idEditText,pwEditText;
    Button btnLogin,btnJoin;

    String sql;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        idEditText = (EditText) findViewById(R.id.editId);
        pwEditText = (EditText) findViewById(R.id.editPwd);

        btnLogin = (Button) findViewById(R.id.login);
        btnJoin = (Button) findViewById(R.id.join);

        //login정보를 저장하기 위한 장소로 db table을 사용
        helper = new DatabaseOpenHelper(loginActivity.this, DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                //아무 입력도 없지만 로그인버튼이 눌린 경우
                if(id.length() == 0 || pw.length() == 0) {
                    //아이디와 비밀번호는 필수 입력사항입니다.
                    Toast toast = Toast.makeText(loginActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    return;
                }

                sql = "SELECT id FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if(cursor.getCount() != 1){
                    //아이디가 틀렸습니다.
                    Toast toast = Toast.makeText(loginActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    return;
                }

                sql = "SELECT pw FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                cursor.moveToNext();
                if(!pw.equals(cursor.getString(0))){
                    //비밀번호가 틀렸습니다.
                    Toast toast = Toast.makeText(loginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }else{
                    //로그인성공
                    Toast toast = Toast.makeText(loginActivity.this, "로그인성공", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    //인텐트 생성 및 호출
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                cursor.close();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //회원가입 버튼 클릭
                Toast toast = Toast.makeText(loginActivity.this, "회원가입 화면으로 이동", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(),joinActivity.class);
                startActivity(intent);
            }
        });

    }
}
