package com.rakantao.pcg.lacostazamboanga.PCGAdmin.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakantao.pcg.lacostazamboanga.R;

public class ProfileShippingListViewHolder extends RecyclerView.ViewHolder {

    public TextView vesselname,vesseltype,vesselorigin,vesseldestination;
    public ImageView imagevessel;
    public View view;

    public ProfileShippingListViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        vesselname = itemView.findViewById(R.id.ListHolderTvVesselName);
        vesseltype = itemView.findViewById(R.id.ListHolderTvVesselType);
        vesselorigin = itemView.findViewById(R.id.ListHolderTvVesselOrigin);
        vesseldestination = itemView.findViewById(R.id.ListHolderTvVesselDestination);
        imagevessel = itemView.findViewById(R.id.ListHolderIVvesselImage);
    }
}
