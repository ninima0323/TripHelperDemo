package com.example.kimnahyeon.testscrollview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimnahyeon.testscrollview.databinding.MainRvItemBinding;

public class TripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ObservableArrayList<Trip> tripList;

    public TripAdapter(Context context, ObservableArrayList<Trip> trips){
        this.context=context;
        this.tripList=trips;
    }

    void setItem(ObservableArrayList<Trip> trip) {
        if (trip == null) {return;}
        this.tripList = trip;
        notifyDataSetChanged();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder{
        MainRvItemBinding binding;
        TripViewHolder(MainRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Trip trip) {
            binding.setVariable(com.example.kimnahyeon.testscrollview.BR.trip, trip);
        }
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainRvItemBinding binding = MainRvItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TripViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Trip trip = tripList.get(position);
        ((TripViewHolder)holder).bind(trip);
        final int pos = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //상세로 이동하기
                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("TripTitle", trip.getTitle());
               // intent.putExtra("tid", trip.getTid());
                intent.putExtra("testData", trip);
                Log.e("!!!!!!!!!!!!!!!!!!!!", ""+trip.toString());
                context.startActivity(intent);
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

                        tripList.remove(pos);
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
//                city.setFstCity("ㅗㅑㅗㅑㅗㅑㅗㅑㅗㅑㅗㅑㅗㅑㅗㅑ");
//                notifyDataSetChanged();

                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        //Log.e("asdd", ""+cityList.size());
        return tripList.size();
    }
}
