package com.example.pandaapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pandaapp.Models.Account;
import com.example.pandaapp.Models.ILoadmoreProduct;
import com.example.pandaapp.Models.Product;
import com.example.pandaapp.R;
import com.example.pandaapp.Retrofit2.APIUltils;
import com.example.pandaapp.Retrofit2.DataClient;
import com.example.pandaapp.Util.GlobalApplication;
import com.example.pandaapp.Util.LoadImage;
import com.example.pandaapp.Util.OtherUltil;
import com.example.pandaapp.view.DetailActivity;
import com.example.pandaapp.view.ListProductCatagoryActivity;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> implements View.OnClickListener, View.OnCreateContextMenuListener {
    Context mctx;
    int layout;
    List<Product> listproduct;
    int stt;
    int VIEW_TYPE_LOADING = 1, VIEW_TYPE_ITEM = 0;
    GlobalApplication globalApplication;
    public boolean isfavorite;
    int setfavorite;


    public AdapterProduct(Context context, int resource, List<Product> object) {
        this.mctx = context;
        this.layout = resource;
        this.listproduct = object;
        globalApplication = (GlobalApplication) mctx.getApplicationContext();

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.item_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return listproduct.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Log.d("AddFavotite", "onBindViewHolder: " + position);
        DataClient dataClient = APIUltils.getData();
        Call<String> stringCall = dataClient.checkFavorite(globalApplication.account.getAccountId(), listproduct.get(position).getProductId());
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String code = "";
                code = response.body();


                if (code.equalsIgnoreCase("yes")) {
                    isfavorite = true;
                    holder.iconheart.setColorFilter(Color.RED);

                } else {
                    isfavorite = false;
                    holder.iconheart.setColorFilter(Color.BLACK);


                }
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        holder.textviewTen.setText(listproduct.get(position).getName());
        holder.textViewGia.setText(OtherUltil.fomattien.format(listproduct.get(position).getPrice() + listproduct.get(position).getDiscount()) + "đ");
        if (listproduct.get(position).getImages().size() != 0) {
            LoadImage.getImageInServer(mctx, listproduct.get(position).getImages().get(0), holder.imageView);
        } else {
            LoadImage.getImageInServer(mctx, "image/image/thumbnail.png", holder.imageView);
        }







       // holder.progressBar.setVisibility(View.GONE);
        holder.linearLayoutItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //globalApplication.product=listproduct.get(position);
                if (globalApplication.product == null) {
                    globalApplication.product = new Product();

                }
                globalApplication.product = listproduct.get(position);

                Intent intent = new Intent(mctx, DetailActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mctx.startActivity(intent);


            }
        });

        if (mctx.toString().contains("FavoriteActivity")) {
            Toast.makeText(mctx, "FavoriteActivity", Toast.LENGTH_SHORT).show();
            holder.iconheart.setVisibility(View.GONE);

        }

        holder.iconheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AddFavotite", "Favorite: " + listproduct.get(position).getProductId() + "---" + isfavorite);

                if (isfavorite == true) {
                    setfavorite = 0;

                } else {
                    setfavorite = 1;
                }
                DataClient dataClient = APIUltils.getData();
                Toasty.normal(mctx, globalApplication.account.getAccountId() + "---" + setfavorite + "->" + listproduct.get(position).getProductId()).show();
                Call<String> stringCall = dataClient.insertFavorite(globalApplication.account.getAccountId(), listproduct.get(position).getProductId(), setfavorite);
                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        int code = Integer.parseInt(response.body());
                        Log.d("AddFavotite", "Them Favorite: " + listproduct.get(position).getProductId() + "---" + setfavorite + "->" + "--->" + code);

                        switch (code) {
                            case 122:
                                holder.iconheart.setColorFilter(Color.RED);
                                isfavorite = !isfavorite;
                                break;
                            case 111:
                                holder.iconheart.setColorFilter(Color.BLACK);
                                break;
                            case 221:
                                holder.iconheart.setColorFilter(Color.BLACK);
                                isfavorite = !isfavorite;
                                break;
                            case 222:
                                holder.iconheart.setColorFilter(Color.RED);
                                break;
                            default:
                                break;
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(mctx, t.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
        Animation animation_cycle = AnimationUtils.loadAnimation(mctx, R.anim.animation_listview);
        holder.linearLayoutItemProduct.setAnimation(animation_cycle);



    }


    @Override
    public int getItemCount() {
        return listproduct.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "SMS");

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, iconheart;
        TextView textviewTen, textViewGia;
        RelativeLayout linearLayoutItemProduct;
        ProgressBar progressBar;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ImageanhSP);
            textviewTen = (TextView) itemView.findViewById(R.id.textviewTenSP);
            textViewGia = (TextView) itemView.findViewById(R.id.textviewGiaSP);
            linearLayoutItemProduct = (RelativeLayout) itemView.findViewById(R.id.itemProduct);
            iconheart = (ImageView) itemView.findViewById(R.id.btn_favorite_ItemProduct);




        }
    }
    


}
