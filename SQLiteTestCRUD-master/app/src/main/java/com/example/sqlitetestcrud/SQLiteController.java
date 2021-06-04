package com.example.sqlitetestcrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteController extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="OrderDB.db";
    private static final int DATABASE_VERSION = 1;
    public SQLiteController(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE orders("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, "+
                "price REAL, "+
                "quantity INTEGER," +
                "time TEXT," +
                "date TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+"orders");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void addOrder(Order order){
        String query = "INSERT INTO orders(name,price,quantity,time,date) VALUES (?,?,?,?,?)";
        String[] args = {order.getOrderName(), Float.toString(order.getPrice()), Integer.toString(order.getQuantity()), order.getTimeOrder(), order.getDateOrder()};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(query,args);
    }

    public int updateOrder(Order order){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", order.getOrderName());
        values.put("price", order.getPrice());
        values.put("quantity", order.getQuantity());
        values.put("time",order.getTimeOrder());
        values.put("date", order.getDateOrder());
        String whereClause = "id= ?";
        String[] whereArgs = {String.valueOf(order.getId())};
        return database.update("orders", values, whereClause, whereArgs);
    }

    public void updateOrder2(Order order){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE orders SET name = '" +order.getOrderName()+ "', price = '" +order.getPrice()+
                "', quantity = '" +order.getQuantity()+ "', time = '" +order.getTimeOrder()+ "', date = '" +order.getDateOrder()+ "' WHERE id = " +order.getId() ;
        db.execSQL(query);
    }


    public int deleteOrder(int id){
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("orders",whereClause,whereArgs);
    }

    public ArrayList<Order> getAllOrder(){
        ArrayList<Order> arrOrder = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.query("orders",null,null,null,null,null,null);

        while ((resultSet!=null) && (resultSet.moveToNext())){
            int id = resultSet.getInt(0);
            String name = resultSet.getString(1);
            float price = resultSet.getFloat(2);
            int quantity = resultSet.getInt(3);
            String time = resultSet.getString(4);
            String date = resultSet.getString(5);
            arrOrder.add(new Order(id,name,price,quantity,time,date));
        }
        resultSet.close();
        return arrOrder;
    }

}
