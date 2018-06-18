package com.rakantao.pcg.lacostazamboanga.PublicUser.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rakantao.pcg.lacostazamboanga.PublicUser.Datas.DataUserHome;
import com.rakantao.pcg.lacostazamboanga.PublicUser.ViewHolders.AdvisoryViewHolder;
import com.rakantao.pcg.lacostazamboanga.R;

import java.util.Collections;
import java.util.List;

public class AdvisoryAdapter extends RecyclerView.Adapter<AdvisoryViewHolder>  {

    List<DataUserHome> userHomeList = Collections.emptyList();
    Context context1;


    public AdvisoryAdapter(List<DataUserHome> list, Context context1)
    {
        this.userHomeList = list;
        this.context1 = context1;
    }

    @NonNull
    @Override
    public AdvisoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.userhome_listrow, parent, false);
        AdvisoryViewHolder holder1 = new AdvisoryViewHolder(v);
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull AdvisoryViewHolder holder, int position) {
        holder.title.setText(userHomeList.get(position).user);
        holder.time.setText(userHomeList.get(position).time);
        holder.message.setText(userHomeList.get(position).content);
    }

    @Override
    public int getItemCount() {
        return userHomeList.size();
    }

    public void insert( DataUserHome data) {
        userHomeList.add(data);
        notifyItemInserted(userHomeList.size());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void clear() {
        userHomeList.clear();
        notifyDataSetChanged();
    }
}
