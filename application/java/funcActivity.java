package com.example.grad;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class funcActivity extends AppCompatActivity {

    //앱에 대한 기능을 설명해주는 페이지

    ListView list;
    String[] titles = {
            "CCTV","화재센서","PHONE","APP info"
    };
    String[] contents= {
            "현재 촬영되고 있는 CCTV 영상을 볼 수 있도록 한다.",
            "현재 작동하고 있는 센서의 상태를 수동으로 확인 가능하다.",
            "앱을 사용 중에 전화를 할 수 있는 기능이다.\n#default 번호: 119",
            "APP의 사용법과 정보를 알려준다."
    };
    Integer[] images = {
            R.drawable.cctv,
            R.drawable.fire,
            R.drawable.phonered,
            R.drawable.info
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appfunc);

        CustomList adapter = new CustomList(funcActivity.this);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        findViewById(R.id.exit).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList(Activity context ) {
            super(context, R.layout.listitem, titles);
            this.context = context;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.listitem, null, true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
            TextView title = (TextView) rowView.findViewById(R.id.title);
            TextView content = (TextView) rowView.findViewById(R.id.contents);


            title.setText(titles[position]);
            imageView.setImageResource(images[position]);
            content.setText(contents[position]);
            return rowView;
        }
    }
}
