package com.example.minh.doan.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.minh.doan.Adapter.MonAnAdapter;
import com.example.minh.doan.Model.MyDatabase;
import com.example.minh.doan.Model.Temp;
import com.example.minh.doan.R;
import com.example.minh.doan.model_class.MonAn;
import com.example.minh.doan.model_class.NhaHang;

import java.util.ArrayList;

public class ChiTietNhaHangActivity extends AppCompatActivity {

    NhaHang nh=null;
    TextView txtten,txtdiachi,txtmota;
    ImageView imgHinh;
    Spinner spinner;
    ArrayList<String> mangLoaiMonAn;

    ArrayAdapter adapterLoaiMonAn;
    final String DATABASE_NAME = "ql_nhahang.sqlite";
    SQLiteDatabase sqLiteDatabase;


    RecyclerView recyclerView;

    MonAnAdapter monAnAdapter;
    ArrayList<MonAn> mangMonAn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nha_hang);
        nh= (NhaHang) getIntent().getSerializableExtra("NH");
        init();
        setValue();

        addEvent();
        getDSMaLoai();

    }

    private void addEvent() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String []s=mangLoaiMonAn.get(i).split("-");
                String maloai=s[0];

                getDSMonAn(maloai);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDSMonAn(String maloai) {
        mangMonAn.clear();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from MonAn where maloai='"+maloai+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            String ten=cursor.getString(1);
            String mota=cursor.getString(2);
            int dongia=cursor.getInt(3);
            byte[] hinhanh=cursor.getBlob(4);
            String maloairow=cursor.getString(5);


            MonAn monAn=new MonAn(ma,ten,mota,dongia,hinhanh,maloairow);
            mangMonAn.add(monAn);

        }
        monAnAdapter.notifyDataSetChanged();
    }

    private void getDSMaLoai() {
        Cursor cursor=sqLiteDatabase.rawQuery("select maloai from NhaHangMonAn where manhahang='"+ Temp.MA_NHA_HANG+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            String ten=getTenLoai(ma);


            mangLoaiMonAn.add(ma+"-"+ten);
        }
        adapterLoaiMonAn.notifyDataSetChanged();
    }

    private String getTenLoai(String maloai) {
        Cursor cursor=sqLiteDatabase.rawQuery("select tenloai from MaLoai where maloai='"+maloai+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            return ma;

        }
       return "";
    }

    private void setValue() {
        Bitmap bitmap= BitmapFactory.decodeByteArray(nh.getHinhanh(),0,nh.getHinhanh().length);

        imgHinh.setImageBitmap(bitmap);
        txtten.setText(nh.getTennh());
        txtdiachi.setText(nh.getDiachi());
        txtmota.setText(nh.getMota());
    }

    private void init() {
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        txtten= (TextView) findViewById(R.id.txttennh);
        txtdiachi= (TextView) findViewById(R.id.txtdiachi);
        txtmota= (TextView) findViewById(R.id.txtmota);
        imgHinh= (ImageView) findViewById(R.id.imgHinh);
        spinner= (Spinner) findViewById(R.id.spinner);


        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mangMonAn=new ArrayList<>();
        monAnAdapter=new MonAnAdapter(this,mangMonAn);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(monAnAdapter);

        mangLoaiMonAn=new ArrayList<>();
        adapterLoaiMonAn=new ArrayAdapter(this,android.R.layout.simple_list_item_1,mangLoaiMonAn);
        spinner.setAdapter(adapterLoaiMonAn);
    }
}
