package com.rakantao.pcg.lacostazamboanga.PublicUser.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rakantao.pcg.lacostazamboanga.R;

public class AdvisoryViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView time;
    public TextView message;

    public AdvisoryViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.tv_title);
        time = itemView.findViewById(R.id.tv_time);
        message = itemView.findViewById(R.id.tv_message);

    }
}
