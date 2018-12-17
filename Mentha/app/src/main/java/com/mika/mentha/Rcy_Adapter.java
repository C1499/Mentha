package com.mika.mentha;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mika.mentha.db.Text;

import java.util.ArrayList;
import java.util.List;

public class Rcy_Adapter  extends RecyclerView.Adapter<Rcy_Adapter.rcy_viewholder>{
private Context context;
private List<Text> textList;


public Rcy_Adapter(Context context, List<Text> textList){
    this.context=context;
    this.textList= textList;
}
    @NonNull
    @Override
    public rcy_viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new rcy_viewholder(LayoutInflater.from(
                viewGroup.getContext()).inflate(
                R.layout.rc_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull rcy_viewholder rcy_viewholder, final int i) {



rcy_viewholder.cardView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       int Maxdata =getItemCount();
        ;


        int a = -(i-Maxdata);
        String b =textList.get(i).getContent();
        Util.showToast(context,"第"+a+"条");
        Intent ShowIntent = new Intent(context,ShowTextActivity.class );
        ShowIntent.putExtra("textShow",b);
        context.startActivity(ShowIntent);
    }
});

     rcy_viewholder.textView.setText(textList.get(i).getContent());
     rcy_viewholder.textView1.setText(textList.get(i).getCreationTime());

    }

    @Override
    public int getItemCount() {
        return textList.size() ;
    }

    class  rcy_viewholder extends RecyclerView.ViewHolder {
        private TextView textView;
        private  TextView textView1;
        private CardView cardView;

        public rcy_viewholder(@NonNull View itemView) {
            super(itemView);
            cardView= (CardView) itemView;
            textView=(itemView).findViewById(R.id.textView);
            textView1=(itemView).findViewById(R.id.textView1);
        }

    }
}
