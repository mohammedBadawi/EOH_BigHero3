package com.example.root.eoh.user;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.eoh.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 13/09/18.
 */

public class UsersAdapter extends ArrayAdapter<User> {

    private Activity context;
    private List<User>list;

    public UsersAdapter(Activity context, List<User> list) {
        super(context , R.layout.item_user , list);
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.item_user , parent , false);

        ImageView img = (ImageView)view.findViewById(R.id.item_user_img);
        TextView name = (TextView) view.findViewById(R.id.item_user_name);


        User user = list.get(position);

        Picasso.with(context).load(user.getImg()).into(img);
        name.setText(user.getName());

        return view;
    }
}
