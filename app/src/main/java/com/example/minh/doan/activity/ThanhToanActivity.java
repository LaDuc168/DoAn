package com.example.minh.doan.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minh.doan.MainActivity;
import com.example.minh.doan.Model.Temp;
import com.example.minh.doan.R;
import com.example.minh.doan.model_class.KhachHang;

import java.util.Random;

import ru.katso.livebutton.LiveButton;

import static com.example.minh.doan.activity.GioHangActivity.sqLiteDatabase;

public class ThanhToanActivity extends AppCompatActivity {


    EditText edttenkh,edtsdt;
    LiveButton btnHoanTat,btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        init();

        addEvent();
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
                String ten=edttenkh.getText().toString();
                String sdt=edtsdt.getText().toString();

                String makh= "MK"+1+new Random().nextInt(3000);

                KhachHang kh=new KhachHang(makh,ten,sdt);

                long check=ThemKhachHang(kh);
                if(check>0){

                    AlertDialog.Builder builder=new AlertDialog.Builder(ThanhToanActivity.this);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Cảm ơn quý khách đã đặt hàng ");
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(ThanhToanActivity.this, MainActivity.class));
                            finish();
                        }
                    });

//        Dialog hopThoai=builder.create();
                    builder.show();


                }
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

    private void init() {
        edttenkh= (EditText) findViewById(R.id.edttenkh);
        edtsdt= (EditText) findViewById(R.id.edtsdt);
        btnHoanTat= (LiveButton) findViewById(R.id.btnHoanTat);
        btnHuy= (LiveButton) findViewById(R.id.btnHuy);
    }
}
