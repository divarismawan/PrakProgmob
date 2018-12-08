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
import com.example.fx504.praktikum.activities.NovelInfoActivity;
import com.example.fx504.praktikum.api.APIUrl;
import com.example.fx504.praktikum.model.RespFavorite;

import java.util.List;

public class FavNovelAdapter extends RecyclerView.Adapter<FavNovelAdapter.ViewHolder> {

    private Context context;
    private List<RespFavorite> favNovel;

    public FavNovelAdapter(Context context, List<RespFavorite> favNovel){
        this.context = context;
        this.favNovel = favNovel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_novel,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final RespFavorite respFavorite = favNovel.get(i);
        viewHolder.id_novel = respFavorite.getId();
        viewHolder.tv_novelTitle.setText(respFavorite.getNovelTitle());
        viewHolder.tv_novelGenre.setText(respFavorite.getNovelGenre());

        String url_file = url();

        //image
        Glide.with(context)
                .load(url_file+respFavorite.getNovelCover())
                .into(viewHolder.iv_novelCover);
        Log.wtf("getNovelCober",respFavorite.getNovelCover());

        //Intent and get id novel
        viewHolder.iv_novelCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), NovelInfoActivity.class);
                intent.putExtra("id_novel",viewHolder.id_novel);
                Toast.makeText(context, ""+viewHolder.id_novel+"  Title :"+respFavorite.getNovelTitle(), Toast.LENGTH_SHORT).show();

                viewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favNovel.size();
    }

    private String url(){
        return APIUrl.BASE_DATA_URL;
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
