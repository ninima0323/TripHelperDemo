package com.example.kimnahyeon.testscrollview.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableArrayList;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kimnahyeon.testscrollview.data.Content;
import com.example.kimnahyeon.testscrollview.databinding.ItemContentBinding;


public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ObservableArrayList<Content> contentList;

    public ContentAdapter(Context context, ObservableArrayList<Content> contents){
        this.context=context;
        this.contentList=contents;
    }

    public void setItems(ObservableArrayList<Content> content) {
        if (content == null) {return;}
        this.contentList = content;
        notifyDataSetChanged();
    }

    public class ContViewHolder extends RecyclerView.ViewHolder{
        ItemContentBinding binding;
        ContViewHolder(ItemContentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Content content) {
            binding.setVariable(com.example.kimnahyeon.testscrollview.BR.content, content);
        }
    }

    @Override
    public ContViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemContentBinding binding = ItemContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Content content = contentList.get(position);
        ((ContViewHolder)holder).bind(content);
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

                        contentList.remove(pos);
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
        return contentList.size();
    }
}
