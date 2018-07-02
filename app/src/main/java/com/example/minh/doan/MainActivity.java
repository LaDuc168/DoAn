package com.example.minh.doan;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.minh.doan.Adapter.MonAnAdapter;
import com.example.minh.doan.Adapter.NhaHangAdapter;
import com.example.minh.doan.Model.MyDatabase;
import com.example.minh.doan.activity.DanhSachMonAn;
import com.example.minh.doan.activity.DanhSachNhaHang;
import com.example.minh.doan.activity.GioHangActivity;
import com.example.minh.doan.model_class.MonAn;
import com.example.minh.doan.model_class.NhaHang;

import java.util.ArrayList;

import ru.katso.livebutton.LiveButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    NhaHangAdapter monAnAdapter;
    ArrayList<NhaHang> listNhaHang;

    final String DATABASE_NAME = "ql_nhahang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    EditText edtTimKiem;
    Button btnTimKiem;
    TextView txtTimKiem;
    RecyclerView recyclerViewTimKiem;
    MonAnAdapter monAnAdapterTimKiem;
    ArrayList<MonAn> mangMonAn;

    ProgressDialog title;

    private ViewFlipper viewFlipper;

    CheckBox checkBoxDiaDiem, checkBoxMonAn;

    int SELECT = 0;

    ImageView imgKetQua;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBar;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        khoitao();
        setData();
        ActionViewFlipper();



        addEvent();

//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);

    }





    private void ActionViewFlipper() {
        ArrayList<Integer> mangquangcao = new ArrayList<>();
        mangquangcao.add(R.drawable.one);
        mangquangcao.add(R.drawable.two);
        mangquangcao.add(R.drawable.three);
        mangquangcao.add(R.drawable.four);
        mangquangcao.add(R.drawable.five);
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView img = new ImageView(getApplicationContext());
            img.setImageResource(mangquangcao.get(i));
            viewFlipper.addView(img);
        }
        viewFlipper.setFlipInterval(3000);//Chạy trong 5s
        viewFlipper.setAutoStart(true);//cho view flipper tự chạy
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void addEvent() {
        edtTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(MainActivity.this.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);

            }
        });


        checkBoxDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxDiaDiem.setChecked(true);
                checkBoxMonAn.setChecked(false);
                SELECT = 0;
            }
        });

        checkBoxMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBoxDiaDiem.setChecked(false);
                checkBoxMonAn.setChecked(true);
                SELECT = 1;
            }
        });
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mangMonAn.clear();

                final String diachi = edtTimKiem.getText().toString();
                if (diachi.equals("")) {
                    Toast.makeText(MainActivity.this, "Nhập địa điểm", Toast.LENGTH_SHORT).show();
                    return;
                }


                title = ProgressDialog.
                        show(MainActivity.this, "Tìm kiếm", "Đang phân tích dữ liệu...");
                title.setCanceledOnTouchOutside(false);
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {
//                        Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);

                        title.dismiss();
                        if (SELECT == 0) {
                            getDSMonAn(diachi);
                        } else if (SELECT == 1) {
                            Toast.makeText(MainActivity.this, "value=" + diachi, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, "dc=" + diachi, Toast.LENGTH_SHORT).show();
                            getDSMonAn_MonAn(diachi);
                        }
                    }
                }.start();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBar.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.menugiohang) {
            Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
//                        String makh = getMAKH(chuoi);
//                        intent.putExtra("KH", makh);
                        startActivity(intent);

//            final Dialog dialog = new Dialog(MainActivity.this);
//            dialog.setTitle("Nhập số điện thoại khách  hàng");
//            dialog.setContentView(R.layout.dialog_tim_kiem);
//            dialog.show();
//
//            final EditText edtTk = dialog.findViewById(R.id.edtmakhach);
//            LiveButton btnTk = dialog.findViewById(R.id.btnOk);
//
//            btnTk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String chuoi = edtTk.getText().toString();
//                    if (chuoi.equals("")) {
//                        Toast.makeText(MainActivity.this, "Bạn chưa nhập mã khách hàng", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    boolean check = CheckMND(chuoi);
//                    if (check) {
//                        Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
//                        String makh = getMAKH(chuoi);
//                        intent.putExtra("KH", makh);
//                        startActivity(intent);
//
//                    } else {
//                        Toast.makeText(MainActivity.this, "Mã người dùng không đúng", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });


        }
        return super.onOptionsItemSelected(item);
    }

    private boolean CheckMND(String m) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from KhachHang where sdt='" + m + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }


    private String getMAKH(String sdt) {
        Cursor cursor = sqLiteDatabase.rawQuery("select makhach from KhachHang where sdt='" + sdt + "'", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            if (cursor.getCount() > 0) {

                String mk = cursor.getString(0);
                return mk;
            }
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_gio_hang, menu);
        return super.onCreateOptionsMenu(menu);
    }





    private void khoitao() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBar = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBar);
        actionBar.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);



        imgKetQua= (ImageView) findViewById(R.id.imgKetQua);
        checkBoxDiaDiem = (CheckBox) findViewById(R.id.checkboxdiadiem);
        checkBoxMonAn = (CheckBox) findViewById(R.id.checkboxmonan);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);


        listNhaHang = new ArrayList<>();
        monAnAdapter = new NhaHangAdapter(getApplicationContext(), listNhaHang);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(monAnAdapter);


        //Ánh xạ
        edtTimKiem = (EditText) findViewById(R.id.edtTimKiem);
        btnTimKiem = (Button) findViewById(R.id.btnTimKiem);
        txtTimKiem = (TextView) findViewById(R.id.txtKetQuaTimKiem);
        recyclerViewTimKiem = (RecyclerView) findViewById(R.id.recycleViewTimKiem);

        mangMonAn = new ArrayList<>();
        monAnAdapterTimKiem = new MonAnAdapter(getApplicationContext(), mangMonAn);
        recyclerViewTimKiem.setHasFixedSize(true);
        recyclerViewTimKiem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewTimKiem.setAdapter(monAnAdapterTimKiem);


    }

    private void getDSMonAn(String diachi) {
        mangMonAn.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("Select  MonAn.mamon,MonAn.tenmon,MonAn.mota,MonAn.dongia,MonAn.hinhanh,MonAn.maloai from NhaHang join NhaHangMonAn on NhaHang.manhahang=NhaHangMonAn.manhahang join MonAn on NhaHangMonAn.maloai=MonAn.maloai where diachi like '%" + diachi + "%'", null);
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
        if (mangMonAn.size() > 0) {
            imgKetQua.setVisibility(View.VISIBLE);
            txtTimKiem.setTextColor(Color.BLUE);
            txtTimKiem.setText("Kết quả tìm kiếm");
            txtTimKiem.setVisibility(View.VISIBLE);
            recyclerViewTimKiem.setVisibility(View.VISIBLE);
        } else {
            imgKetQua.setVisibility(View.GONE);
            txtTimKiem.setTextColor(Color.RED);
            txtTimKiem.setVisibility(View.VISIBLE);
            txtTimKiem.setText("Không tìm thấy kết quả nào");

            recyclerViewTimKiem.setVisibility(View.GONE);
        }
        monAnAdapterTimKiem = new MonAnAdapter(getApplicationContext(), mangMonAn);
        recyclerViewTimKiem.setHasFixedSize(true);
        recyclerViewTimKiem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewTimKiem.setAdapter(monAnAdapterTimKiem);
    }


    private void getDSMonAn_MonAn(String monan) {
        mangMonAn.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from MonAn where tenmon like '%" + monan + "%'",
                null);
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
        if (mangMonAn.size() > 0) {
            imgKetQua.setVisibility(View.VISIBLE);
            txtTimKiem.setTextColor(Color.BLUE);
            txtTimKiem.setText("Kết quả tìm kiếm");
            txtTimKiem.setVisibility(View.VISIBLE);
            recyclerViewTimKiem.setVisibility(View.VISIBLE);
        } else {
            imgKetQua.setVisibility(View.GONE);
            txtTimKiem.setTextColor(Color.RED);
            txtTimKiem.setVisibility(View.VISIBLE);
            txtTimKiem.setText("Không tìm thấy kết quả nào");
            recyclerViewTimKiem.setVisibility(View.GONE);
        }
        monAnAdapterTimKiem = new MonAnAdapter(getApplicationContext(), mangMonAn);
        recyclerViewTimKiem.setHasFixedSize(true);
        recyclerViewTimKiem.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewTimKiem.setAdapter(monAnAdapterTimKiem);
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

            listNhaHang.add(nh);

        }
        monAnAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_trangchu) {
            startActivity(new Intent(MainActivity.this,MainActivity.class));
            finish();
        }
        if (id == R.id.nap_monan) {
            startActivity(new Intent(MainActivity.this,DanhSachMonAn.class));
        }
        if (id == R.id.nav_nhahang) {
            startActivity(new Intent(MainActivity.this,DanhSachNhaHang.class));
        }
        if (id == R.id.nav_thongtinungdung) {
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Thông tin ứng dụng");
            builder.setMessage("Với các ứng dụng Walmart dành cho IPhone, việc tìm kiếm các sản phẩm chất lượng và giá cạnh tranh nhất rất đơn giản với một vài thao tác nhanh chóng.\n" +
                    "\n" +
                    "Công cụ này giúp bạn lập kế hoạch trước, nhiều tính năng mua sắm thông minh và nhiều sản phẩm giảm giá giúp bạn có thể mua sắm được những sản phẩm với mức giá tốt nhất");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

//        Dialog hopThoai=builder.create();
            builder.show();   }

        return true;
    }
}
