package com.example.aliakbar.focaloid.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aliakbar.focaloid.R;
import com.example.aliakbar.focaloid.fragments.ProductDetailFragment;
import com.example.aliakbar.focaloid.model.Movie;
import com.example.aliakbar.focaloid.rest.Constant;

import java.util.List;

/**
 * Created by user on 1/23/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.HomeViewHolder>{

    private Context mContext;
    private List<Movie> movies;


    public class HomeViewHolder extends RecyclerView.ViewHolder {

        public TextView title, count;
        public ImageView thumbnail, overflow;

        public HomeViewHolder(View view, Context mContext, List<Movie> movies) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public ProductAdapter(Context mContext, List<Movie> movies) {
        this.mContext = mContext;
        this.movies = movies;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_card, parent, false);

        HomeViewHolder homeViewHolder =new HomeViewHolder(itemView,mContext,movies);

        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, final int position) {


        holder.title.setText(movies.get(position).getTitle());;
        holder.count.setText("Price :"+movies.get(position).getVoteAverage().toString());

        if (movies.get(position).getBackdropPath() != null) {
            String images = movies.get(position).getBackdropPath().toString();
            // Log.d("PicChecking", images);

            // loading album cover using Glide library
            Glide.with(mContext)
                    .load(Constant.tmdb_img_url+images)
                    .into(holder.thumbnail);
        }
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = new ProductDetailFragment();
//                FragmentManager fragmentManager = ((Activity)mContext).getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content_frame, fragment);
//                fragmentTransaction.commit();


                Fragment fragment=new ProductDetailFragment();
                ((Activity)mContext).getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,fragment,fragment.getClass().getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_card, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
