package com.mika.mentha;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Util {
    private static Toast toast;
    public static void showToast(Context context, String content){
        if ( toast ==null){
            toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }
        else {
            toast.setText(content);
        }
        toast.show();
    }

}
