package com.example.minh.doan.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.minh.doan.Adapter.MonAnAdapter;
import com.example.minh.doan.Model.MyDatabase;
import com.example.minh.doan.R;
import com.example.minh.doan.model_class.MonAn;

import java.util.ArrayList;

import static com.example.minh.doan.R.id.imgKetQua;
import static com.example.minh.doan.activity.GioHangActivity.sqLiteDatabase;

public class DanhSachMonAn extends AppCompatActivity {

    RecyclerView recyclerViewTimKiem;
    MonAnAdapter monAnAdapterTimKiem;
    ArrayList<MonAn> mangMonAn;

    final String DATABASE_NAME = "ql_nhahang.sqlite";
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_mon_an);

        init();
        getDSMonAn();
    }

    private void getDSMonAn() {
        mangMonAn.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("Select  MonAn.mamon,MonAn.tenmon,MonAn.mota,MonAn.dongia,MonAn.hinhanh,MonAn.maloai from NhaHang join NhaHangMonAn on NhaHang.manhahang=NhaHangMonAn.manhahang join MonAn on NhaHangMonAn.maloai=MonAn.maloai", null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String mota = cursor.getString(2);
            int dongia = cursor.getInt(3);
            byte[] hinhanh = cursor.getBlob(4);
            String maloairow = cursor.getString(5);


            MonAn monAn = new MonAn(ma, ten, mota, dongia, hinhanh, maloairow);
            mangMonAn.add(monAn);

        }

        monAnAdapterTimKiem.notifyDataSetChanged();


    }

    private void init() {

        recyclerViewTimKiem= (RecyclerView) findViewById(R.id.recycleViewTimKiem);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        mangMonAn = new ArrayList<>();
        monAnAdapterTimKiem = new MonAnAdapter(getApplicationContext(), mangMonAn);
        recyclerViewTimKiem.setHasFixedSize(true);
        recyclerViewTimKiem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewTimKiem.setAdapter(monAnAdapterTimKiem);

    }
}
