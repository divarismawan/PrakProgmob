package com.example.fx504.praktikum.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fx504.praktikum.R;
import com.example.fx504.praktikum.activities.InfoNovelActivity;
import com.example.fx504.praktikum.api.APIUrl;
import com.example.fx504.praktikum.model.ResShowNovel;

import java.util.List;


public class NewNovelAdapter extends RecyclerView.Adapter<NewNovelAdapter.ViewHolder> {

    private Context context;
    private List<ResShowNovel> showNovels;

    public NewNovelAdapter(Context context, List<ResShowNovel> showNovels) {
        this.context = context;
        this.showNovels = showNovels;
    }

    //set view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_novel,viewGroup,false);

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           }
       });

        return new ViewHolder(view);
    }

    //input data to view
    @Override
    public void onBindViewHolder(@NonNull final NewNovelAdapter.ViewHolder viewHolder, int i) {
        final ResShowNovel resShowNovel = showNovels.get(i);
        viewHolder.id_novel = resShowNovel.getId();
        viewHolder.tv_novelTitle.setText(resShowNovel.getNovelTitle());
        viewHolder.tv_novelGenre.setText(resShowNovel.getNovelGenre());

        String url_file = url();

        //image
        Glide.with(context)
                .load(url_file+resShowNovel.getNovelCover())
                .into(viewHolder.iv_novelCover);
        Log.wtf("getNovelCober",resShowNovel.getNovelCover());

        //Intent and get id novel
        viewHolder.iv_novelCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), InfoNovelActivity.class);
                intent.putExtra("id_novel",viewHolder.id_novel);
                Toast.makeText(context, ""+viewHolder.id_novel+"  Title :"+resShowNovel.getNovelTitle(), Toast.LENGTH_SHORT).show();

                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

    }

    private String url(){
        return APIUrl.BASE_DATA_URL;
    }

    @Override
    public int getItemCount() {
        return showNovels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        int id_novel;
        TextView tv_novelTitle;
        TextView tv_novelGenre;
        ImageView iv_novelCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_novelTitle = itemView.findViewById(R.id.tv_novel_title);
            tv_novelGenre = itemView.findViewById(R.id.tv_novel_genre);
            iv_novelCover = itemView.findViewById(R.id.iv_novel_img);
        }

    }
}
