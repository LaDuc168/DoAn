package com.example.minh.doan.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.minh.doan.Adapter.MonAnAdapter;
import com.example.minh.doan.Adapter.NhaHangAdapter;
import com.example.minh.doan.Model.MyDatabase;
import com.example.minh.doan.R;
import com.example.minh.doan.model_class.MonAn;
import com.example.minh.doan.model_class.NhaHang;

import java.util.ArrayList;

public class DanhSachNhaHang extends AppCompatActivity {

    RecyclerView recyclerViewTimKiem;
    NhaHangAdapter monAnAdapterTimKiem;
    ArrayList<NhaHang> mangMonAn;
    final String DATABASE_NAME = "ql_nhahang.sqlite";
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nha_hang);
        init();
        setData();

    }


    private void setData() {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from NhaHang", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String mota = cursor.getString(2);
            String diachi = cursor.getString(3);
            byte[] hinhanh = cursor.getBlob(4);
            NhaHang nh = new NhaHang(ma, ten, mota, diachi, hinhanh);

            mangMonAn.add(nh);

        }
        monAnAdapterTimKiem.notifyDataSetChanged();
    }
    private void init() {

        recyclerViewTimKiem= (RecyclerView) findViewById(R.id.recycleViewTimKiem);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        mangMonAn = new ArrayList<>();
        monAnAdapterTimKiem = new NhaHangAdapter(getApplicationContext(), mangMonAn);
        recyclerViewTimKiem.setHasFixedSize(true);
        recyclerViewTimKiem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewTimKiem.setAdapter(monAnAdapterTimKiem);

    }
}
