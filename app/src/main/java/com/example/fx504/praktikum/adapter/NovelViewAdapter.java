package com.example.fx504.praktikum.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fx504.praktikum.ReadNovelActivity;
import com.example.fx504.praktikum.model.Novel;
import com.example.fx504.praktikum.R;

import java.util.List;

public class NovelViewAdapter extends RecyclerView.Adapter<NovelViewAdapter.MyViewHolder> {

    private Context context;
    private List<Novel> novelList;
    Dialog dialog;

    public NovelViewAdapter(Context context, List<Novel> myNovel) {
        this.context = context;
        this.novelList = myNovel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        final LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.cardview_novel, viewGroup ,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.preview_novel);



        myViewHolder.cv_novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Line ("+String.valueOf(myViewHolder.getAdapterPosition())+")", Toast.LENGTH_SHORT).show();

//                ImageView iv_imageDialog = dialog.findViewById(R.id.ci_imgDialog);
//                TextView  tv_titleDialog = dialog.findViewById(R.id.tv_titleDialog);
//                TextView  tv_descDialog  = dialog.findViewById(R.id.tv_descDialog);
//
//                tv_titleDialog.setText(novelList.get(myViewHolder.getAdapterPosition()).getTitle());
//                tv_descDialog.setText(novelList.get(myViewHolder.getAdapterPosition()).getDescription());
//                iv_imageDialog.setImageResource(novelList.get(myViewHolder.getAdapterPosition()).getThubnail());
//                dialog.show();

                Intent intent = new Intent(context, ReadNovelActivity.class);
                intent.putExtra("novel_cover",novelList.get(myViewHolder.getAdapterPosition()).getThubnail());
                intent.putExtra("novel_title",novelList.get(myViewHolder.getAdapterPosition()).getTitle());
                intent.putExtra("novel_genre",novelList.get(myViewHolder.getAdapterPosition()).getGenre());
                intent.putExtra("novel_desc", novelList.get(myViewHolder.getAdapterPosition()).getDescription());
                context.startActivity(intent);

            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.iv_novel_img.setImageResource(novelList.get(position).getThubnail());
        myViewHolder.tv_novel_title.setText(novelList.get(position).getTitle());
        myViewHolder.tv_novel_genre.setText(novelList.get(position).getGenre());

    }

    @Override
    public int getItemCount() {
        return novelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_novel_img;
        TextView tv_novel_title;
        TextView tv_novel_genre;
        CardView cv_novel;

        public MyViewHolder(View itemView){
            super(itemView);


            cv_novel       = itemView.findViewById(R.id.cv_novel);
            iv_novel_img   = itemView.findViewById(R.id.iv_novel_img);
            tv_novel_title = itemView.findViewById(R.id.tv_novel_title);
            tv_novel_genre = itemView.findViewById(R.id.tv_novel_genre);

        }
    }
}
