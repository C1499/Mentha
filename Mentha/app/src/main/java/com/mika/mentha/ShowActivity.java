package com.mika.mentha;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mika.mentha.db.Text;
import com.mika.mentha.db.Users;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowActivity extends AppCompatActivity {
    private TextView content;
    private CircleImageView imageView;
    private RecyclerView recyclerView;
    private Rcy_Adapter rcy_adapter;
    private static final int RESULT_LOAD_IMAGE = 2;
    List<Text> textLista;
    private FloatingActionButton btHide;
    private int a;
    private String b;
    private String c;
    private int d;
    private int i;
    @Override
    protected void onResume() {
        super.onResume();
        i++; //很弱智的判断方法，没事能用
        rcy_adapter = new Rcy_Adapter(this, textLista);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rcy_adapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        if (i==1){
            int spacingInPixels = 30; //间距，我也不知道什么单位，你自己调
            recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels)); //调用辅助类
        }
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        //头像
        imageView = findViewById(R.id.headimage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        //展示日记
        recyclerView = findViewById(R.id.Recycle_vie);
        a = getIntent().getIntExtra("Number", 0);
        b = getIntent().getStringExtra("Username");
        c = getIntent().getStringExtra("user_name");

        //d= getIntent().getIntExtra("Number2",0);
        //  List<Text>texts= null;
        //查询当前登陆用户的日记
        textLista = DataSupport.where("users_id = ?", String.valueOf(a)).find(Text.class);

        // Log.d("aafsfs=--", "onCreate: "+textLista.get(0).getContent());

//if (textList.size()>0){
//
//
//        Log.d("aafsfs=--", "onCreate: "+textList.get(0).getContent());
//        Log.d("aafsfs=--", "onCreate: "+a);
//}
     /*  if (!textList.isEmpty()){
           for (Text text :textList){

              Log.d("aa==" , "onCreate: "+text);


              //texts.add(text);
           }*/


    /*  if (texts!=null){
    Log.d("aafsfs=--", "onCreate: "+texts.size());
      }*/

/*if (!texts.isEmpty()){
    rcy_adapter = new Rcy_Adapter(this,texts);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager) ;
    recyclerView.setAdapter(rcy_adapter);
}*/

        btHide = findViewById(R.id.fab);

        //日记内容点击事件

      /* // content = (TextView)findViewById(R.id.textView);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ShowActivity.this,ShowTextActivity.class);
                startActivity(intent2);
            }
        });*/

        //悬浮菜单

        btHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowActivity.this, TextActivity.class);
                intent.putExtra("Username3", c);
                intent.putExtra("Username2", b);
                startActivity(intent);
            }
        });
    }

    //123456
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                //图片回调
                if (resultCode == RESULT_OK && null != data) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(data.getData()));
                        imageView.setImageBitmap(bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                break;
            default:
                break;


        }

    }
}
