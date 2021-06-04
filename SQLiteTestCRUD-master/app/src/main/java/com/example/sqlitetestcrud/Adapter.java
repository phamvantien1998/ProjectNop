package com.example.sqlitetestcrud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Order> arrOrder;
    private ArrayList<Order> arrOrder2 = new ArrayList<>();
    private float totalMoney = 0;
    MainActivity mainActivity;
    public Adapter(ArrayList<Order> arrOrder, MainActivity mainActivity) {
        this.arrOrder = arrOrder;
        this.mainActivity=mainActivity;
        this.arrOrder2.addAll(arrOrder);
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Order order = arrOrder.get(position);
        if (order == null){
            return;
        } else {
            holder.tvID.setText("ID Order: " + order.getId());
            holder.tvName.setText("Name Order: " + order.getOrderName());
            holder.tvPrice.setText("Price: " + order.getPrice());
            holder.tvQuantity.setText("Quantity: " + order.getQuantity());
            holder.tvTime.setText("Time date: " + order.getTimeOrder());
            holder.tvDate.setText("Order date: " + order.getDateOrder());

            totalMoney = totalMoney + arrOrder.get(position).getPrice() * arrOrder.get(position).getQuantity();
            mainActivity.totalMoney = totalMoney;
        }
        int idOrder = arrOrder.get(position).getId();
        String nameOrder = arrOrder.get(position).getOrderName();
        float price = arrOrder.get(position).getPrice();
        int quantity = arrOrder.get(position).getQuantity();
        String time = arrOrder.get(position).getTimeOrder();
        String date = arrOrder.get(position).getDateOrder();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.edtId.setText(""+idOrder);
                mainActivity.edtName.setText(nameOrder);
                mainActivity.edtPrice.setText(""+price);
                mainActivity.edtQuantity.setText(""+quantity);
                mainActivity.edtTime.setText(time);
                mainActivity.edtDate.setText(date);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrOrder != null){
            return arrOrder.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvID, tvName, tvPrice,tvQuantity, tvTime, tvDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrOrder.clear();
        if (charText.length() == 0) {
            arrOrder.addAll(arrOrder2);
        } else {
            for (Order o : arrOrder2) {
                if (o.getOrderName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrOrder.add(o);
                }
            }
        }
        notifyDataSetChanged();
    }
}
