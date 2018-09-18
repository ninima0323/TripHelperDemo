package com.example.kimnahyeon.testscrollview.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableArrayList;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimnahyeon.testscrollview.data.Memo;
import com.example.kimnahyeon.testscrollview.databinding.ItemMemoBinding;

public class MemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ObservableArrayList<Memo> memoList;

    public MemoAdapter(Context context, ObservableArrayList<Memo> memos){
        this.context=context;
        this.memoList=memos;
    }

    public void setItems(ObservableArrayList<Memo> memo) {
        if (memo == null) {return;}
        this.memoList = memo;
        notifyDataSetChanged();
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder{
        ItemMemoBinding binding;
        MemoViewHolder(ItemMemoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Memo memo) {
            binding.setVariable(com.example.kimnahyeon.testscrollview.BR.memo, memo);
        }
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMemoBinding binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MemoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Memo memo = memoList.get(position);
        ((MemoViewHolder)holder).bind(memo);
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

                        memoList.remove(pos);
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
        return memoList.size();
    }
}
