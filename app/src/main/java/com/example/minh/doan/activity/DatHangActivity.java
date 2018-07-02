package com.example.minh.doan.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minh.doan.MainActivity;
import com.example.minh.doan.Model.MyDatabase;
import com.example.minh.doan.Model.Temp;
import com.example.minh.doan.R;
import com.example.minh.doan.model_class.DonHang;
import com.example.minh.doan.model_class.KhachHang;

import java.util.Random;

import ru.katso.livebutton.LiveButton;

import static com.example.minh.doan.R.layout.activity_dat_hang;

public class DatHangActivity extends AppCompatActivity {

    EditText edtmonan,edtsoluong,edthoten,edtsdt;
    LiveButton btnHoanTat,btnHuy;

    String MA_KHACH="";

    final String DATABASE_NAME = "ql_nhahang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        init();

        if(Temp.MA_KHACH.equals("")){
            MA_KHACH="MK"+1+new Random().nextInt(3000);
            Temp.MA_KHACH=MA_KHACH;
        }

        ThemGioHang();





    }

    private void ThemGioHang() {
        String ten="Lã văn đức";
        String sdt="0963510217";
        String maKhach =Temp.MA_KHACH;


        int soluong=1;



//        KhachHang kh=new KhachHang(MA_KHACH,ten,sdt);

        DonHang dh=new DonHang(maKhach, Temp.MA_NHA_HANG,Temp.MA_MON_AN,soluong);

//        long check1=ThemKhachHang(kh);
        long check2=ThemDonHang(dh);

        if(check2>0){
            Toast.makeText(DatHangActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(DatHangActivity.this, "Mặt hàng này quý khách đã đặt.", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(DatHangActivity.this, MainActivity.class));
        finish();

    }

    private void setValue() {
        edtmonan.setText(getTenMonAn(Temp.MA_MON_AN));
    }


    private String getTenMonAn(String mamon) {
        Cursor cursor=sqLiteDatabase.rawQuery("select tenmon from MonAn where mamon='"+mamon+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            return ma;

        }
        return "";
    }

    private String getMaKhach(String sdt) {
        Cursor cursor=sqLiteDatabase.rawQuery("select makhach from KhachHang where sdt='"+sdt+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            return ma;

        }
        return "";
    }

    private void addEvent() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

    private long ThemKhachHang(KhachHang kh) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("makhach", kh.getMa());
        contentValues.put("tenkhach", kh.getTen());
        contentValues.put("sdt", kh.getSdt());

       return sqLiteDatabase.insert("KhachHang", null, contentValues);

    }

    private long ThemDonHang(DonHang dh) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("makhach", dh.getMakhach());
        contentValues.put("manhahang", dh.getManh());
        contentValues.put("mamon", dh.getMamon());
        contentValues.put("soluong", dh.getSoluong());

        return sqLiteDatabase.insert("DonHang", null, contentValues);

    }

    private void init() {
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        edtmonan= (EditText) findViewById(R.id.edtmonan);
        edtsoluong= (EditText) findViewById(R.id.edtsoluong);
        edthoten= (EditText) findViewById(R.id.edttenkh);
        edtsdt= (EditText) findViewById(R.id.edtsdt);
        btnHoanTat= (LiveButton) findViewById(R.id.btnHoanTat);
        btnHuy= (LiveButton) findViewById(R.id.btnHuy);
    }
}
