package com.baway.recyclerviewdemo2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by peng on 2017/9/6.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> list;
    private OnItemClickListener onItemClickListener;
    private static final int TYPE1 = 0;
    private static final int TYPE2 = 1;

    public interface OnItemClickListener {
        public void onItemClick(int position);

        public void onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE1) {
            ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
            return itemViewHolder;
        } else {
            ItemViewHolder2 itemViewHolder2 = new ItemViewHolder2(LayoutInflater.from(context).inflate(R.layout.item2, parent, false));
            return itemViewHolder2;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) ==TYPE1) {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.tv.setText(list.get(position));
                itemViewHolder.ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                });
                itemViewHolder.ll.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemLongClick(position);
                        }
                        return true;
                    }
                });
            }
        }else{
            if (holder instanceof ItemViewHolder2) {
                ItemViewHolder2 itemViewHolder2 = (ItemViewHolder2) holder;
                itemViewHolder2.tv2.setText(list.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position%2==0?TYPE1:TYPE2;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private LinearLayout ll;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

    private class ItemViewHolder2 extends RecyclerView.ViewHolder {
        TextView tv2;
        public ItemViewHolder2(View itemView) {
            super(itemView);
            tv2 = itemView.findViewById(R.id.tv2);
        }
    }

    public void loadMore(List<String> moreList) {
        list.addAll(moreList);
        notifyDataSetChanged();
    }

    public void refresh(List<String> l) {
        list.clear();
        list.addAll(l);
        notifyDataSetChanged();
    }

    public void addData(int position) {
        list.add(position, "新增数据");
        notifyItemInserted(position);

    }

    public void removeData(int postition) {
        list.remove(postition);
        notifyItemRemoved(postition);
    }
}
