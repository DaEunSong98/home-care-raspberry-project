package com.example.grad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class joinActivity extends AppCompatActivity {

    //회원가입하는 액티비티 _ 로그인페이지에서 회원가입버튼이 눌리면 실행

    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;

    EditText idEditText;
    EditText pwEditText;
    Button btnJoin;

    String sql;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        idEditText = (EditText) findViewById(R.id.editId);
        pwEditText = (EditText) findViewById(R.id.editPwd);

        btnJoin = (Button) findViewById(R.id.btn);

        helper = new DatabaseOpenHelper(joinActivity.this, DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                //아무 입력이 없는데 회원가입버튼이 눌린 경우
                if (id.length() == 0 || pw.length() == 0) {
                    //아이디와 비밀번호는 필수 입력사항입니다.
                    Toast toast = Toast.makeText(joinActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                sql = "SELECT id FROM " + helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if (cursor.getCount() != 0) {
                    //존재하는 아이디입니다.
                    Toast toast = Toast.makeText(joinActivity.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {    //회원가입 완료 -> 로그인 페이지로 이동
                    helper.insertUser(database, id, pw);
                    Toast toast = Toast.makeText(joinActivity.this, "가입이 완료되었습니다. 로그인을 해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}