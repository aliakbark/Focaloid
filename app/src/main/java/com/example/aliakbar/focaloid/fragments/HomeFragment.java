package com.example.aliakbar.focaloid.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.example.aliakbar.focaloid.adapter.ProductAdapter;
import com.example.aliakbar.focaloid.model.AllProducts;
import com.example.aliakbar.focaloid.model.AllProductsResults;
import com.example.aliakbar.focaloid.R;
import com.example.aliakbar.focaloid.rest.ApiClient;
import com.example.aliakbar.focaloid.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "1f55851baac668d665ad59a6384318dc";

    private ProductAdapter adapter;
    private List<AllProducts> data;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_layout_changer)
    Button btn_layout_changer;

    RecyclerView.LayoutManager linearLayoutManager;
    RecyclerView.LayoutManager gridLayoutManager;
    int flag=0;

    Context mcontext;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this,rootView);
        mcontext=rootView.getContext();

        gridLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getActivity());
//
//        movies = new ArrayList<>();
//        adapter = new ProductAdapter(mcontext, movies);

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(rootView.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadJSON();
//        prepareAlbums();


        return rootView;
    }

    private void loadJSON(){

        final ProgressDialog loading = ProgressDialog.show(mcontext,"Fetching Data","Please wait...",false,false);


        ApiInterface api = ApiClient.getApiService();

        Call<AllProductsResults> call = api.getProducts("100","0","0","0","1");

        call.enqueue(new Callback<AllProductsResults>() {
            @Override
            public void onResponse(Call<AllProductsResults> call, Response<AllProductsResults> response) {

                data = response.body().getData();

                //recyclerView.setAdapter(new ProductAdapter(movies, R.layout.dashboard_card, mcontext));

                adapter = new ProductAdapter(mcontext,data);
                recyclerView.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<AllProductsResults> call, Throwable t) {
                Toast.makeText(mcontext,"json failure",Toast.LENGTH_LONG).show();
                Log.d("Error",t.getMessage());
            }
        });
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public HomeFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.btn_layout_changer)
    public void onClick_btn_layout_changer() {

        if (flag == 0) {

            float deg = btn_layout_changer.getRotation() + 180F;
            btn_layout_changer.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());


            btn_layout_changer.setBackgroundResource(R.drawable.grid_layout);
            recyclerView.setLayoutManager(linearLayoutManager);

            flag = 1;

        } else {

            float deg = btn_layout_changer.getRotation() + 180F;
            btn_layout_changer.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

            btn_layout_changer.setBackgroundResource(R.drawable.linear_layout);
            recyclerView.setLayoutManager(gridLayoutManager);

            flag = 0;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}