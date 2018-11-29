package com.example.fx504.praktikum.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fx504.praktikum.R;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_novel,viewGroup,false);
        return new ViewHolder(view);
    }


    //input data to view
    @Override
    public void onBindViewHolder(@NonNull NewNovelAdapter.ViewHolder viewHolder, int i) {
        ResShowNovel resShowNovel = showNovels.get(i);
        viewHolder.id_novel = resShowNovel.getId();
        viewHolder.tv_novelTitle.setText(resShowNovel.getNovelTitle());
        viewHolder.tv_novelGenre.setText(resShowNovel.getNovelGenre());

        //image
        Glide.with(context).load(resShowNovel.getNovelCover())
                .into(viewHolder.iv_novelCover);

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
