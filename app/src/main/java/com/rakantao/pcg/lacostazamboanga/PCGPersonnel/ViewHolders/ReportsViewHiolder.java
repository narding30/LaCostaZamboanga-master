package com.rakantao.pcg.lacostazamboanga.PCGPersonnel.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakantao.pcg.lacostazamboanga.R;

public class ReportsViewHiolder extends RecyclerView.ViewHolder {

    public TextView tvVesselName;
    public TextView tvTimeUploaded;
    public TextView tvInspector;
    public ImageView tvImageVessel;

    public ReportsViewHiolder(View itemView) {
        super(itemView);

        tvVesselName = itemView.findViewById(R.id.reportVesselName);
        tvTimeUploaded = itemView.findViewById(R.id.reportDate);
        tvInspector = itemView.findViewById(R.id.reportInspector);
        tvImageVessel = itemView.findViewById(R.id.reportImageVesselThumb);

    }
}
