package com.example.minh.doan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh.doan.Model.Temp;
import com.example.minh.doan.R;
import com.example.minh.doan.activity.ChiTietMonAnActivity;
import com.example.minh.doan.activity.ChiTietNhaHangActivity;
import com.example.minh.doan.model_class.MonAn;

import java.util.ArrayList;

/**
 * Created by Minh on 5/18/2018.
 */

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ItemHolder> {
    Context context;
    ArrayList<MonAn> sanPhamArrayList;


    public MonAnAdapter(Context context, ArrayList<MonAn> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @Override
    public MonAnAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nha_hang, null);
        MonAnAdapter.ItemHolder itemHolder = new MonAnAdapter.ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final MonAn nh = sanPhamArrayList.get(position);
        holder.txtTen.setText(nh.getTenmon());

        Bitmap bitmap= BitmapFactory.decodeByteArray(nh.getHinhanh(),0,nh.getHinhanh().length);

        holder.imgAnh.setImageBitmap(bitmap);
        holder.imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietMonAnActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("MA",nh);
                context.startActivity(intent);

                Temp.MA_MON_AN=nh.getMamon();

//                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            }
        });
        

    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgAnh;
        public TextView txtTen;

        public ItemHolder(View itemView) {
            super(itemView);
            imgAnh = (ImageView) itemView.findViewById(R.id.imghinhanh);
            txtTen = (TextView) itemView.findViewById(R.id.txttennh);
            
        }
    }
}
