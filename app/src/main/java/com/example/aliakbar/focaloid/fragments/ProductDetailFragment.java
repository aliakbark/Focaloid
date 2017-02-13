package com.example.aliakbar.focaloid.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aliakbar.focaloid.MainActivity;
import com.example.aliakbar.focaloid.R;
import com.example.aliakbar.focaloid.rest.ApiClient;
import com.example.aliakbar.focaloid.rest.ApiInterface;
import com.example.aliakbar.focaloid.rest.Constant;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetailFragment extends Fragment {

    Context mcontext;

    String productid;

    @BindView(R.id.single_product_image)
    ImageView thumbnail;
    @BindView(R.id.single_product_title)
    TextView single_product_title;
    @BindView(R.id.single_product_price)
    TextView single_product_price;
    @BindView(R.id.single_product_description)
    TextView single_product_description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Product Detail");
        ButterKnife.bind(this,rootView);

        Bundle bundle = getArguments();
        productid = bundle.getString("ProPkId");

        mcontext=rootView.getContext();

        loadJSON();

        return rootView;
    }

    private void loadJSON(){

        final ProgressDialog loading = ProgressDialog.show(mcontext,"Fetching Data","Please wait...",false,false);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIPlug request = retrofit.create(APIPlug.class);*/

        ApiInterface api = ApiClient.getApiService();

        Call<JsonObject> call = api.getProductDetail(productid);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful())
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        if(jsonObject.has("success"))
                        {
                            if(jsonObject.getString("success").equalsIgnoreCase("1"))
                            {
                                JSONObject dataobject=jsonObject.getJSONObject("data");
                                if(dataobject.isNull("1")) {

                                    JSONArray productArray = dataobject.getJSONArray("product_data");

                                    for (int i = 0; i < productArray.length(); i++) {

                                        String proName = productArray.getJSONObject(i).getString("pro_name");
                                        single_product_title.setText(proName);
                                        single_product_price.setText("Price : "+productArray.getJSONObject(i).getString("pro_price"));


                                        // get our html content
                                        String htmlAsString = productArray.getJSONObject(i).getString("pro_desc");      // used by WebView
                                        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView

                                        // set the html content on a TextView



                                        single_product_description.setText(htmlAsSpanned);

                                        String images=productArray.getJSONObject(i).getString("product_images");



                                        List<String> list = Lists.newArrayList(Splitter.on(",").splitToList(images));

                                        String img=list.get(0);

                                        // loading album cover using Glide library
                                        Glide.with(mcontext).load(Constant.imageUrlLargeResolution+img).into(thumbnail);

                                        loading.dismiss();

                                    }
                                }
                            }

                        }else{

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(mcontext,"json failure",Toast.LENGTH_LONG).show();
                Log.d("Error",t.getMessage());
            }
        });
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}