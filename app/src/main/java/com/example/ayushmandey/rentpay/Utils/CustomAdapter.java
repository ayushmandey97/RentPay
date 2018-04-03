package com.example.ayushmandey.rentpay.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ayushmandey.rentpay.R;

import java.util.List;


public class CustomAdapter extends ArrayAdapter<Post> {

    List<Post> arr;

    public CustomAdapter(@NonNull Context context, List<Post> arr ) {
        super(context, R.layout.layout_view_post, arr );
        this.arr = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inf = LayoutInflater.from(getContext());

        View customView = inf.inflate(R.layout.layout_view_post , parent , false );

        ImageView iv = ( ImageView ) customView.findViewById( R.id.post_image);
        TextView tv1 = ( TextView ) customView.findViewById( R.id.price );
        TextView tv2 = ( TextView ) customView.findViewById( R.id.image_time_posted );
        TextView tv3 = ( TextView ) customView.findViewById( R.id.image_caption );

        Post item = arr.get(position);

        Glide.with(getContext()).load(item.getImage()).into( iv );

        tv1.setText( item.getTitle() );
        tv2.setText( item.getProductAge() );
        tv3.setText( item.getDesc());

        return customView;
    }

}
