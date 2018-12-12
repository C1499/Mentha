package com.mika.mentha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowTextActivity extends AppCompatActivity {
        private TextView showtime;
        private TextView showtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);
       String textShow =getIntent().getStringExtra("textShow");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = new Date(System.currentTimeMillis());
        showtime = (TextView)findViewById(R.id.timeshowView);
        showtime.setText(simpleDateFormat.format(date));
        showtext = (TextView)findViewById(R.id.textView2);
        showtext.setText(textShow);

    }
}
