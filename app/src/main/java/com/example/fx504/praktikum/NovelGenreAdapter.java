package com.example.fx504.praktikum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.fx504.praktikum.novels.NovelGenreActivity;

public class NovelGenreAdapter extends FragmentStatePagerAdapter {

    public NovelGenreAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                setColorButton(1);
                break;
            case 1:
                setColorButton(2);
                break;
            case 2:
                setColorButton(3);
                break;
            case 3:
                setColorButton(4);
                break;
            case 4:
                setColorButton(5);
                break;
        }
        Log.wtf("posisi", String.valueOf(position));


        Fragment pageFragment = new FragmentGenre();
        Bundle bundle = new Bundle();
        bundle.putInt("numberGenre",position+1);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    public void setColorButton(int position){
        NovelGenreActivity novelGenreActivity = new NovelGenreActivity();
        novelGenreActivity.colorPositionButton(position);
    }

}
