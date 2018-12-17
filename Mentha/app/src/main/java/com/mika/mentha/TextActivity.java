package com.mika.mentha;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.mika.mentha.db.Text;
import com.mika.mentha.db.Users;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TextActivity extends AppCompatActivity {
    private TextView timeshow;
    private EditText textView;
    private ImageView jianpan;
    private ImageView yuying;
    private ImageView tupian;
    private static final int RESULT_LOAD_IMAGE = 2;

    protected static final int RESULT_SPEECH = 1;
    private ImageButton btnSpeak;
    private TextView txtText;
    private String a;
    private  String b;
    private Util util;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        a = getIntent().getStringExtra("Username3");
        b= getIntent().getStringExtra("Username2");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = new Date(System.currentTimeMillis());
        timeshow = (TextView)findViewById(R.id.timeView);
        timeshow.setText(simpleDateFormat.format(date));

        jianpan = findViewById(R.id.jianpan);
        textView = findViewById(R.id.textEZdit);


        jianpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()){
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
                }
                else{
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        yuying = findViewById(R.id.yuying);
        yuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
        tupian = findViewById(R.id.tuku);
        tupian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }

        });
        yuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpeechUtility.createUtility(TextActivity.this,SpeechConstant.APPID + "=5c0dd9f5");
                RecognizerDialog recognizerDialog = new RecognizerDialog(TextActivity.this,null);
                recognizerDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//语种，这里可以有zh_cn和en_us
                recognizerDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//设置口音，这里设置的是汉语普通话 具体支持口音请查看讯飞文档，
                recognizerDialog.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");//设置编码类型
                recognizerDialog.setListener(new RecognizerDialogListener() {
                    @Override
                        public void onResult(RecognizerResult recognizerResult, boolean b) { //结果处理方法
                        if (!b) {
//                            Log.i("讯飞识别的结果", recognizerResult.getResultString());
                            String resultString = recognizerResult.getResultString();
                            JSONObject jsonObject = JSON.parseObject(resultString); //JSON解析
                            JSONArray jsonArray = jsonObject.getJSONArray("ws");
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONArray jsonArray1 = jsonObject1.getJSONArray("cw");
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                                String w = jsonObject2.getString("w");
                                stringBuffer.append(w);
                            }
                            String Sbresult = stringBuffer.toString();
                            textView.append(Sbresult); //追加内容
                            Log.i("识别结果", Sbresult);
                        }
                    }

                    @Override
                    public void onError(SpeechError speechError) {
                        Log.e("返回的错误码", speechError.getErrorCode() + "");
                    }
                });
                recognizerDialog.show();
            }
        });

    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
           /*     ImageSpan imageSpan = new ImageSpan(this,R.drawable.dui);
                SpannableString spannableString = new SpannableString("test");
                spannableString.setSpan(imageSpan,0,spannableString.length(),SpannableString.SPAN_POINT_MARK);
                textView.append("\n");
                Editable editable = textView.getEditableText();
                int selectionIndex = textView.getSelectionStart();
                spannableString.getSpans(0, spannableString.length(), ImageSpan.class);
               editable.insert(selectionIndex,spannableString);
               textView.append("\n\n");
*/ //回调里做




                Text text = new Text();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                Date date = new Date(System.currentTimeMillis());
                String textv = textView.getText().toString();

           List<Users> users = DataSupport.findAll(Users.class);
           Users usersa = null;
           Boolean userature = false;

           for (Users users1:users){
               if (users1.getUsername().equals(b)||users1.getUsername().equals(a)){
                   userature=true;
                   usersa=users1;

                   break;
               }
           }
                Log.d("aaaaa========", "onOptionsItemSelected: "+b);
                Log.d("aaaaa========", "onOptionsItemSelected: "+a);
                Log.d("aaaaa========", "onOptionsItemSelected: "+usersa);
if (usersa!=null&&userature){

    Log.d("11111=========", "onOptionsItemSelected: "+usersa);
    text.setUsers(usersa);
    text.setContent(textv);
    text.setCreationTime(simpleDateFormat.format(date));
    text.save();
    textView.setText("");
    Util.showToast(TextActivity.this,"保存成功");
    Intent intentaa  = new Intent(TextActivity.this,ShowActivity.class);
    intentaa.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    intentaa.putExtra("Number",usersa.getId());
    intentaa.putExtra("user_name",b);
    startActivity(intentaa);


    }

    }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtText.setText(text.get(0));
                }
                break;
            case RESULT_LOAD_IMAGE:
                    //图片回调
                if (resultCode == RESULT_OK && null != data) {
                try {
                    Bitmap bitmap =BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(data.getData()));
                    ImageSpan imageSpan = new ImageSpan(this,bitmap);
                    SpannableString spannableString = new SpannableString(data.getData()+"");
                    spannableString.setSpan(imageSpan,0,spannableString.length(),SpannableString.SPAN_POINT_MARK);
                    textView.append("\n");
                    Editable editable = textView.getEditableText();
                    int selectionIndex = textView.getSelectionStart();
                    spannableString.getSpans(0, spannableString.length(), ImageSpan.class);
                    editable.insert(selectionIndex,spannableString);
                    textView.append("\n");



                }catch (Exception e){
                    e.printStackTrace();
                }

                }

                break;
          default:
              break;



        }
    }

}
