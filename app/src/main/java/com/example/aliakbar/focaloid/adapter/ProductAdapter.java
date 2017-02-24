package com.example.aliakbar.focaloid.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.aliakbar.focaloid.model.AllProducts;
import com.example.aliakbar.focaloid.rest.Constant;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by user on 1/23/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    private Context mContext;
    private List<AllProducts> products;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, price;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view, Context mContext, List<AllProducts> products) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public ProductAdapter(Context mContext, List<AllProducts> products) {
        this.mContext = mContext;
        this.products = products;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_card, parent, false);

        MyViewHolder myViewHolder =new MyViewHolder(itemView,mContext,products);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final AllProducts product = products.get(position);
        holder.title.setText(product.getProName());
        holder.price.setText("$ " + product.getProPrice().toString());




//        if (product.getProductImages() != null) {
//            // Picasso.with(mContext).load(Constant.imageUrlLargeResolution+ productSingleItem.getProductImageL0()).placeholder(R.drawable.product_image_default).fit().into(myViewHolder.productImage);
//
//
//            String images=product.getProductImages();
//
//            List<String> list = Lists.newArrayList(Splitter.on(",").splitToList(images));
//
//            String img=list.get(0);
//
//            // loading album cover using Glide library
//            Glide.with(mContext).load(Constant.imageUrlLargeResolution+img).into(holder.thumbnail);
//
//        }

       if (product.getProductImages() != null) {
           String images = product.getProductImages().toString();

           List<String> list = Lists.newArrayList(Splitter.on(",").splitToList(images));
           String img=list.get(0);
//           Log.d("PicChecking", img);


           // loading album cover using Glide library
           Glide.with(mContext)
                   .load(Constant.imageUrlLargeResolution+img)
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



            Bundle args = new Bundle();
            args.putString("ProPkId",  product.getProPkId());

                Fragment fragment=new ProductDetailFragment();
                fragment.setArguments(args);
                ((Activity)mContext).getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame,fragment)
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
                    Toast.makeText(mContext, "Add to cart", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}
