package com.example.fx504.praktikum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fx504.praktikum.novels.NovelGenreActivity;

public class FragmentGenre extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_genre, container,false);

        TextView tv_number = view.findViewById(R.id.tv_number);

        Bundle bundle = getArguments();
        int pageNumber = bundle.getInt("numberGenre");


        tv_number.setText(Integer.toString(pageNumber));

//        Toast.makeText(getContext(), ""+pageNumber, Toast.LENGTH_SHORT).show();
//        setColorButton(pageNumber);



        return view;
    }

    public void setColorButton(int position){
        NovelGenreActivity novelGenreActivity = new NovelGenreActivity();
        novelGenreActivity.colorPositionButton(position);
    }
}
