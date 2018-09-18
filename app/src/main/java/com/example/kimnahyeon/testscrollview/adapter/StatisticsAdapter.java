package com.example.kimnahyeon.testscrollview.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableArrayList;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimnahyeon.testscrollview.data.Statistics;
import com.example.kimnahyeon.testscrollview.databinding.ItemStatisticsBinding;

public class StatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    ObservableArrayList<Statistics> statList;

    public StatisticsAdapter(Context context, ObservableArrayList<Statistics> statistics){
        this.context=context;
        this.statList=statistics;
    }

    public void setItems(ObservableArrayList<Statistics> statistics) {
        if (statistics == null) {return;}
        this.statList = statistics;
        notifyDataSetChanged();
    }

    public class StatViewHolder extends RecyclerView.ViewHolder{
        ItemStatisticsBinding binding;
        StatViewHolder(ItemStatisticsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Statistics statistics) {
            binding.setVariable(com.example.kimnahyeon.testscrollview.BR.statistics, statistics);
        }
    }

    @Override
    public StatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemStatisticsBinding binding = ItemStatisticsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new StatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Statistics statistics = statList.get(position);
        ((StatViewHolder)holder).bind(statistics);
        final int pos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //추가항목 보이기
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setMessage("삭제하시겠습니까?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        statList.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, getItemCount());
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return statList.size();
    }
}
