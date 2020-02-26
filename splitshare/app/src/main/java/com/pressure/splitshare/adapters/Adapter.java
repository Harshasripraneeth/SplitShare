package com.pressure.splitshare.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pressure.splitshare.R;
import com.pressure.splitshare.Mate;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    ArrayList<Mate> person;
    itemclicked activity;
    Context context;
    public  interface itemclicked
    {
        void onitemclicked(int index);
    }
    public Adapter(Context context, ArrayList list)
    {
        this.context =context;
        activity = (itemclicked)context;
        person = list;
    }
    class ViewHolder extends  RecyclerView.ViewHolder
    {
        TextView tv2;
        TextView tv3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onitemclicked(person.indexOf((Mate) v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(person.get(i));
        viewHolder.tv2.setText(person.get(i).getName());
        viewHolder.tv3.setText(person.get(i).getTel());
    }

    @Override
    public int getItemCount() {
        try{
            return person.size();
        }
        catch (NullPointerException e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return 0;

    }
}
