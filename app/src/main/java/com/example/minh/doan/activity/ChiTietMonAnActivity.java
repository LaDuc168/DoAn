package com.example.minh.doan.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minh.doan.Adapter.AdapterBinhLuanMonAn;
import com.example.minh.doan.Model.MyDatabase;
import com.example.minh.doan.R;
import com.example.minh.doan.model_class.MonAn;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static com.example.minh.doan.R.drawable.bl;
import static com.example.minh.doan.R.id.btnguibl;
import static com.example.minh.doan.R.id.txtbl;
import static com.example.minh.doan.activity.GioHangActivity.TongTien;

public class ChiTietMonAnActivity extends AppCompatActivity {

    TextView txtten,txtmota,txtdongia,txtmaloai;
    ImageView imgHinh;
    Button btnDatHang;

    MonAn monAn;

    final String DATABASE_NAME = "ql_nhahang.sqlite";
    SQLiteDatabase sqLiteDatabase;


    ArrayList<Integer> mangHA;

    ListView listViewBL;
    EditText edtBL;
    Button btnGuiBL;

    AdapterBinhLuanMonAn adapterBinhLuanMonAn;
    ArrayList<String > mangBL;

    TextView txtBinhLuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        init();

        monAn= (MonAn) getIntent().getSerializableExtra("MA");
        setValue();
        addEvent();
        LayBinhLuan(monAn.getMamon());
    }

    private void setValue() {
        Bitmap bitmap= BitmapFactory.decodeByteArray(monAn.getHinhanh(),0,monAn.getHinhanh().length);

        imgHinh.setImageBitmap(bitmap);
        txtten.setText(monAn.getTenmon());
        txtmota.setText(monAn.getMota());

        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getNumberInstance(locale);
        String format = numberFormat.format(monAn.getDongia());

        txtdongia.setText(format+" VNĐ");
        txtmaloai.setText(getTenLoai(monAn.getMaloai()));
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

    private void addEvent() {
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ChiTietMonAnActivity.this,DatHangActivity.class));
                finish();
            }
        });

        btnGuiBL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bl=edtBL.getText().toString();
                if(bl.equals("")){
                    Toast.makeText(ChiTietMonAnActivity.this, "Bạn chưa nhập bình luận", Toast.LENGTH_SHORT).show();
                    return;
                }

                long check = ThemBL(bl);
                if(check>0){
                    Toast.makeText(ChiTietMonAnActivity.this, "Bình luận của bạn được ghi nhận", Toast.LENGTH_SHORT).show();
                    LayBinhLuan(monAn.getMamon());
                    edtBL.setText("");
                }else {
                    Toast.makeText(ChiTietMonAnActivity.this, "Bình luận thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long ThemBL(String  nd){
        ContentValues contentValues = new ContentValues();
        contentValues.put("mamon", monAn.getMamon());
        contentValues.put("mabl",(30+ new Random().nextInt(5321)));
        contentValues.put("noidung",nd);

        return sqLiteDatabase.insert("BinhLuan", null, contentValues);

    }

    private void init() {
        txtBinhLuan= (TextView) findViewById(R.id.txtbinhluan);
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        txtten= (TextView) findViewById(R.id.txttenmon);
        txtmota= (TextView) findViewById(R.id.txtmota);
        txtdongia= (TextView) findViewById(R.id.txtdongia);
        txtmaloai= (TextView) findViewById(R.id.txtloaimon);
        btnDatHang= (Button) findViewById(R.id.btnDatHang);
        imgHinh= (ImageView) findViewById(R.id.imgHinh);

        mangBL=new ArrayList<>();
        mangHA=new ArrayList<>();
        mangHA.add(R.drawable.mot);
        mangHA.add(R.drawable.hai);
        mangHA.add(R.drawable.ba);
        mangHA.add(R.drawable.bon);
        mangHA.add(R.drawable.nam);

        listViewBL= (ListView) findViewById(R.id.listViewBinhLuan);
        edtBL= (EditText) findViewById(R.id.edtnhapbinhluan);
        btnGuiBL= (Button) findViewById(btnguibl);

        adapterBinhLuanMonAn=new AdapterBinhLuanMonAn(this,mangBL,mangHA);
        listViewBL.setAdapter(adapterBinhLuanMonAn);

    }

    private void LayBinhLuan(String mamon) {
        mangBL.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select noidung from BinhLuan where mamon='" + mamon + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String blks = cursor.getString(0);
            mangBL.add(blks);
        }
        txtBinhLuan.setText("Bình luận ( "+cursor.getCount()+" )");
        adapterBinhLuanMonAn=new AdapterBinhLuanMonAn(this,mangBL,mangHA);
        listViewBL.setAdapter(adapterBinhLuanMonAn);
    }
}
