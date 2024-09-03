package com.example.crcqtrmanagment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;
    private boolean isAdmin; // Admin status flag

    public MyAdapter(Context context, List<DataClass> dataList, boolean isAdmin) {
        this.context = context;
        this.dataList = dataList;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass currentItem = dataList.get(position);

        if (currentItem != null) {
            holder.recQtr.setText(currentItem.getQtrNo());
            holder.recEmpName.setText(currentItem.getEmployeName());
            holder.recDesignation.setText(currentItem.getDesignation());
            holder.recNEIS.setText(currentItem.getNeisno());
            holder.recUnit.setText(currentItem.getUnit());
            holder.recLegal.setText(currentItem.getOther());
            holder.recPhoneNo.setText(currentItem.getPhoneNo());
            holder.recRemark.setText(currentItem.getRemark());

            if (isAdmin) {
                holder.recRelativelayout.setOnClickListener(v -> {
                    Intent intent = new Intent(context, Detail.class);
                    intent.putExtra("QtrNo", currentItem.getQtrNo());
                    intent.putExtra("EmployeName", currentItem.getEmployeName());
                    intent.putExtra("Designation", currentItem.getDesignation());
                    intent.putExtra("Neisno", currentItem.getNeisno());
                    intent.putExtra("Unit", currentItem.getUnit());
                    intent.putExtra("Other", currentItem.getOther());
                    intent.putExtra("PhoneNo", currentItem.getPhoneNo());
                    intent.putExtra("Remark", currentItem.getRemark());
                    context.startActivity(intent);
                });
            } else {
                holder.recRelativelayout.setOnClickListener(null); // Disable click for non-admin
            }
        } else {
            Log.e("MyAdapter", "DataClass object at position " + position + " is null");
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList) {
        this.dataList = searchList;
        notifyDataSetChanged();
    }
}


class MyViewHolder extends RecyclerView.ViewHolder {

    TextView recQtr, recEmpName, recDesignation, recNEIS, recUnit, recLegal, recPhoneNo, recRemark;
    RelativeLayout recRelativelayout;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        // Initialize views here
        recQtr = itemView.findViewById(R.id.recQtr);
        recEmpName = itemView.findViewById(R.id.recEmpName);
        recDesignation = itemView.findViewById(R.id.recDesignation);
        recNEIS = itemView.findViewById(R.id.recNEIS);
        recUnit = itemView.findViewById(R.id.recUnit);
        recLegal = itemView.findViewById(R.id.recLegal);
        recPhoneNo = itemView.findViewById(R.id.recPhoneNo);
        recRemark = itemView.findViewById(R.id.recRemark);
        recRelativelayout = itemView.findViewById(R.id.recRelativelayout);

    }
}
