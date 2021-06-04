package com.example.sqlitetestcrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private Button btnAdd, btnUpdate, btnDelete, btnSearchByID, btnSearchByName, btnGetAll, getTotalMoney;
    public EditText edtId, edtName, edtPrice, edtQuantity, edtTime, edtDate ;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private SQLiteController sqLite;
    public float totalMoney;
    SearchView searchView;
    int t1h,t1m;
    ArrayList<Order> arrOrder12 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sqLite = new SQLiteController(this);
        ArrayList<Order> arrOrder = sqLite.getAllOrder();
        adapter = new Adapter(arrOrder,MainActivity.this);
        recyclerView.setAdapter(adapter);
        edtTime = findViewById(R.id.edt_timeorder);
        DatePickerDialog.OnDateSetListener setListener;
        edtDate = findViewById(R.id.edt_dateorder);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t1h = hourOfDay;
                        t1m = minute;
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(0,0,0,t1h,t1m);
                        edtTime.setText(DateFormat.format("hh:mm aa",calendar1));
                    }
                },12,0,false
                );
                timePickerDialog.updateTime(t1h,t1m);
                timePickerDialog.show();
            }
        });

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"-"+month+"-"+year;
                        edtDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String time = edtTime.getText().toString();
            String date = edtDate.getText().toString();
            try {
                float price = Float.parseFloat(edtPrice.getText().toString());
                int quantity = Integer.parseInt(edtQuantity.getText().toString());
                Order order = new Order(name, price, quantity, time, date);
                sqLite.addOrder(order);
                Toast.makeText(getApplicationContext(),"Them thanh cong order!",Toast.LENGTH_LONG).show();
            } catch (NumberFormatException e){
                System.out.println(e);
            }
            ArrayList<Order> arrOrder14 = sqLite.getAllOrder();
            adapter = new Adapter(arrOrder14,MainActivity.this);
            recyclerView.setAdapter(adapter);
        });

        btnUpdate.setOnClickListener(v -> {
            edtId.setEnabled(false);
            String name = edtName.getText().toString();
            String time = edtTime.getText().toString();
            String date = edtDate.getText().toString();
            try {
                int id = Integer.parseInt(edtId.getText().toString().trim());
                float price = Float.parseFloat(edtPrice.getText().toString().trim());
                int quantity = Integer.parseInt(edtQuantity.getText().toString().trim());
                Order order = new Order(id,name, price, quantity, time, date);
                sqLite.updateOrder(order);
                Toast.makeText(getApplicationContext(),"Da update order!",Toast.LENGTH_LONG).show();
                edtId.setEnabled(true);
            } catch (NumberFormatException e){
                System.out.println(e);
            }
            ArrayList<Order> arrOrder13 = sqLite.getAllOrder();
            adapter = new Adapter(arrOrder13,MainActivity.this);
            recyclerView.setAdapter(adapter);
        });

        btnGetAll.setOnClickListener(v -> {
             arrOrder12 = sqLite.getAllOrder();
            adapter = new Adapter(arrOrder12,MainActivity.this);
            recyclerView.setAdapter(adapter);
        });

        btnDelete.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(edtId.getText().toString());
                sqLite.deleteOrder(id);
                Toast.makeText(getApplicationContext(),"Da xoa order id =  "+id+"!",Toast.LENGTH_LONG).show();
                edtId.setEnabled(true);
            } catch (NumberFormatException e){
                System.out.println(e);
            }
            ArrayList<Order> arrOrder1 = sqLite.getAllOrder();
            adapter = new Adapter(arrOrder1,MainActivity.this);
            recyclerView.setAdapter(adapter);
        });


        getTotalMoney.setOnClickListener(view -> {
            ArrayList<Order> listOrder = sqLite.getAllOrder();
            adapter = new Adapter(listOrder,MainActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Tổng tiền các order: "+ totalMoney, Toast.LENGTH_LONG).show();
        });
        searchView.setOnQueryTextListener(this);
    }


    @Override
    protected void onDestroy() {
        sqLite.close();
        super.onDestroy();
    }

    private void initView() {
        btnAdd = findViewById(R.id.btn_add);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnGetAll = findViewById(R.id.btn_getall);
        edtId = findViewById(R.id.edt_idorder);
        edtName = findViewById(R.id.edt_ordername);
        edtPrice = findViewById(R.id.edt_price);
        edtQuantity = findViewById(R.id.edt_quantity);
        edtTime = findViewById(R.id.edt_timeorder);
        edtDate = findViewById(R.id.edt_dateorder);
        recyclerView = findViewById(R.id.rv_order);
        getTotalMoney=findViewById(R.id.getTotalMoney);
        searchView=findViewById(R.id.searchView);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
