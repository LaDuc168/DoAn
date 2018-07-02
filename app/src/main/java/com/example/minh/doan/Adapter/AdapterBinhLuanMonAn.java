package com.example.minh.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minh.doan.R;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by LaVanDuc on 5/24/2018.
 */

public class AdapterBinhLuanMonAn extends BaseAdapter {
    Context myContext;
    ArrayList<String> mang;
    ArrayList<Integer> mangHA;

    public AdapterBinhLuanMonAn(Context myContext, ArrayList<String> mang, ArrayList<Integer> mangHA) {
        this.myContext = myContext;
        this.mang = mang;
        this.mangHA = mangHA;
    }

    @Override
    public int getCount() {
        return mang.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_binh_luan,null);
        TextView txtbl=convertView.findViewById(R.id.txtbinhluan);
        CircleImageView img=convertView.findViewById(R.id.imgHAItem);
        int id=0+new Random().nextInt(mangHA.size()-1);
        img.setImageResource(mangHA.get(id));
        String bl=mang.get(position);
        txtbl.setText(bl);
        return convertView;
    }
}
