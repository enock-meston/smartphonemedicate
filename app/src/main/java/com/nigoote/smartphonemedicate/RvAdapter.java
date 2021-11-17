package com.nigoote.smartphonemedicate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<DataModel> dataModelArrayList;

    public RvAdapter(Context ctx, ArrayList<DataModel> dataModelArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }


    @NonNull
    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_one, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.MyViewHolder holder, int position) {
        holder.nameH.setText(dataModelArrayList.get(position).getNames());
        holder.phoneH.setText(dataModelArrayList.get(position).getPhone());
        holder.diseaseH.setText(dataModelArrayList.get(position).getDisease());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameH, phoneH,diseaseH;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameH = (TextView) itemView.findViewById(R.id.namestxt);
            phoneH = (TextView) itemView.findViewById(R.id.phonetxt);
            diseaseH = (TextView) itemView.findViewById(R.id.diseasetxt);

        }
    }
}
