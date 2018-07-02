package com.example.minh.doan.Adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minh.doan.Model.MyDatabase;
import com.example.minh.doan.Model.Temp;
import com.example.minh.doan.R;
import com.example.minh.doan.activity.ChiTietMonAnActivity;
import com.example.minh.doan.activity.GioHangActivity;
import com.example.minh.doan.model_class.DonHang;
import com.example.minh.doan.model_class.MonAn;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.R.attr.bitmap;
import static android.R.attr.id;

/**
 * Created by Minh on 5/18/2018.
 */

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ItemHolder> {
    Context context;
    ArrayList<DonHang> sanPhamArrayList;

    final String DATABASE_NAME = "ql_nhahang.sqlite";
    SQLiteDatabase sqLiteDatabase;
    int TongTien=0;
    TextView txtTT;

    public GioHangAdapter(Context context, ArrayList<DonHang> sanPhamArrayList,TextView txtTT) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
        txtTT=txtTT;
        sqLiteDatabase = MyDatabase.initDatabase((Activity) context, DATABASE_NAME);
    }

    @Override
    public GioHangAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gio_hang, null);
        GioHangAdapter.ItemHolder itemHolder = new GioHangAdapter.ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        final DonHang nh = sanPhamArrayList.get(position);

        holder.txtTennh.setText(getTenNhaHang(nh.getManh()));
        holder.txtTenmonan.setText(getTenMon(nh.getMamon()));

        holder.btnsoluong.setText(nh.getSoluong()+"");

        //Sự kiện

        holder.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn muốn xóa không?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        sqLiteDatabase.delete("DonHang", "makhach=? and manhahang=? and mamon=?", new String[]{
                                nh.getMakhach(),nh.getManh(),nh.getMamon()
                        });

                        getDSKhachHang();

                    }
                });
//        Dialog hopThoai=builder.create();
                builder.show();
            }
        });
        holder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl=nh.getSoluong();

                if(sl==1){
                    Toast.makeText(context, "Không được giảm hơn", Toast.LENGTH_SHORT).show();
                    return;
                }
                sl--;

                ContentValues contentValues = new ContentValues();
                contentValues.put("makhach", nh.getMakhach());
                contentValues.put("manhahang", nh.getManh());
                contentValues.put("mamon", nh.getMamon());
                contentValues.put("soluong", sl);

                sqLiteDatabase.update("DonHang", contentValues, "makhach=? and manhahang=? and mamon=?", new String[]{
                        nh.getMakhach(),nh.getManh(),nh.getMamon()
                });

                getDSKhachHang();
                notifyDataSetChanged();
            }
        });

        holder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl=nh.getSoluong();

                sl++;

                ContentValues contentValues = new ContentValues();
                contentValues.put("makhach", nh.getMakhach());
                contentValues.put("manhahang", nh.getManh());
                contentValues.put("mamon", nh.getMamon());
                contentValues.put("soluong", sl);

                sqLiteDatabase.update("DonHang", contentValues, "makhach=? and manhahang=? and mamon=?", new String[]{
                        nh.getMakhach(),nh.getManh(),nh.getMamon()
                });
                getDSKhachHang();
                notifyDataSetChanged();
            }
        });


        

    }

    private void getDSKhachHang( ) {
        TongTien=0;
        sanPhamArrayList.clear();
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
            sanPhamArrayList.add(dh);

        }

        Locale locale=new Locale("vi","VN");
        NumberFormat numberFormat=NumberFormat.getNumberInstance(locale);
        String format = numberFormat.format(TongTien);
        GioHangActivity.CapNhatGD(format);
        notifyDataSetChanged();
    }

    private int getDonGia(String mamon) {
        Cursor cursor=sqLiteDatabase.rawQuery("select dongia from MonAn where mamon='"+mamon+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            int ma=cursor.getInt(0);
            return ma;

        }
        return 1;
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

    private String getTenNhaHang(String manh) {
        Cursor cursor=sqLiteDatabase.rawQuery("select tennhahang from NhaHang where manhahang='"+manh+"'",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            return ma;

        }
        return "";
    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView txtTennh;
        public TextView txtTenmonan;
        public ImageView imgCancel;

        Button btntru,btnsoluong,btncong;

        public ItemHolder(View itemView) {
            super(itemView);
            txtTennh = (TextView) itemView.findViewById(R.id.txttennh);
            txtTenmonan = (TextView) itemView.findViewById(R.id.txttenmonan);
            btntru=itemView.findViewById(R.id.btntru);
            btnsoluong=itemView.findViewById(R.id.btnsoluong);
            btncong=itemView.findViewById(R.id.btncong);
            imgCancel=itemView.findViewById(R.id.imgCancel);

        }
    }
}
