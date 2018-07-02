package com.example.minh.doan.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minh.doan.Adapter.GioHangAdapter;
import com.example.minh.doan.Adapter.MonAnAdapter;
import com.example.minh.doan.Model.MyDatabase;
import com.example.minh.doan.Model.Temp;
import com.example.minh.doan.R;
import com.example.minh.doan.model_class.DonHang;
import com.example.minh.doan.model_class.KhachHang;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GioHangActivity extends AppCompatActivity {

    final String DATABASE_NAME = "ql_nhahang.sqlite";
    static SQLiteDatabase sqLiteDatabase;

    static RecyclerView recyclerView;
    static GioHangAdapter gioHangAdapter;

    static ArrayList<DonHang> mangDH;
    public  static TextView txtTongTien;

    String  MA_KHACH="";
   static int TongTien=0;

    Button btnThanhToan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        init();
//        MA_KHACH=getIntent().getStringExtra("KH");
//        Temp.MA_KHACH=MA_KHACH;

        getDSKhachHang();
        addEvent();


    }

    private void addEvent() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GioHangActivity.this,ThanhToanActivity.class));
            }
        });
    }

    private void init() {

        btnThanhToan= (Button) findViewById(R.id.btnThanhToan);

        txtTongTien= (TextView) findViewById(R.id.txtTongTien);
        mangDH=new ArrayList<>();
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);

        recyclerView= (RecyclerView) findViewById(R.id.recycleView);

        gioHangAdapter = new GioHangAdapter(this, mangDH,txtTongTien);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setAdapter(gioHangAdapter);
    }
    public static void CapNhatGD(String tt){
        txtTongTien.setText("Tổng tiền: "+tt+" VNĐ");
    }

    public static void getDSKhachHang( ) {
        TongTien=0;
        mangDH.clear();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from DonHang",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String makhach=cursor.getString(0);
            String manhahang=cursor.getString(1);
            String monan=cursor.getString(2);
            int soluong=cursor.getInt(3);
            int donGia = getDonGia(monan);

            TongTien+=soluong*donGia;

            DonHang dh=new DonHang(makhach,manhahang,monan,soluong);
            mangDH.add(dh);

        }
        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getNumberInstance(locale);
        String format = numberFormat.format(TongTien);

        CapNhatGD(format);
        gioHangAdapter.notifyDataSetChanged();
    }


    private String getTenMon(String mamon) {
        Cursor cursor=sqLiteDatabase.rawQuery("select tenmon from MonAn where mamon='"+mamon+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            return ma;

        }
        return "";
    }

    private  static int getDonGia(String mamon) {
        Cursor cursor=sqLiteDatabase.rawQuery("select dongia from MonAn where mamon='"+mamon+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            int ma=cursor.getInt(0);
            return ma;

        }
        return 1;
    }

    private String getTenNhaHang(String manh) {
        Cursor cursor=sqLiteDatabase.rawQuery("select tennhahang from NhaHang where manhahang='"+manh+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            return ma;

        }
        return "";
    }

    private KhachHang getTenKhach(String manh) {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from KhachHang where makhach='"+manh+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            String ten=cursor.getString(1);
            String sdt=cursor.getString(2);
            return new KhachHang(ma,ten,sdt);

        }
        return null;
    }
}
