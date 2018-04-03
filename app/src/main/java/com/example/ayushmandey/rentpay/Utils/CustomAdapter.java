package com.example.ayushmandey.rentpay.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ayushmandey.rentpay.R;
import com.example.ayushmandey.rentpay.Utils.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sarthak Kukreja on 4/1/2018.
 */

public class CustomAdapter extends ArrayAdapter<Post> implements Filterable {

    List<Post> arr;
    List<Post> backup;

    public CustomAdapter(@NonNull Context context, List<Post> arr ) {
        super(context, R.layout.layout_view_post, arr );
        this.arr = arr;
        backup = arr;
    }


    @Override
    public int getCount() {
        return arr.size();
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

        if (position % 2 == 1) {
            customView.setBackgroundColor(Color.BLUE);
        } else {
            customView.setBackgroundColor(Color.CYAN);
        }


        return customView;
    }

    @NonNull
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                arr = backup;

                FilterResults results = new FilterResults();
                ArrayList<Post> FilteredArrayNames = new ArrayList<>();

                constraint = constraint.toString().toLowerCase();

                for( int i = 0 ; i < arr.size() ; i++ )
                {
                    Post cur = arr.get(i);

                    if ( cur.getTitle().toLowerCase().contains(constraint.toString().toLowerCase()) )
                    {
                        FilteredArrayNames.add(cur);
                    }
                }

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;

                return results;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                arr = (List<Post>) filterResults.values;
                notifyDataSetChanged();
            }

        };
    };
}