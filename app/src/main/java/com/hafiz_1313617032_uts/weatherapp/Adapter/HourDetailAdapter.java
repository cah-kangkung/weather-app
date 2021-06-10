package com.hafiz_1313617032_uts.weatherapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.Hour;
import com.hafiz_1313617032_uts.weatherapp.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HourDetailAdapter extends RecyclerView.Adapter<HourDetailAdapter.ListViewHolder> {

    private ArrayList<Hour> listHour;

    public HourDetailAdapter(ArrayList<Hour> listHour) {
        this.listHour = listHour;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_hour, parent, false);
        HourDetailAdapter.ListViewHolder listViewHolder = new HourDetailAdapter.ListViewHolder(view);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        Picasso.get().load("https:" + listHour.get(position).getCondition().getIcon()).into(holder.tv_icon);

        String lastUpdate = listHour.get(position).getTime();
        SimpleDateFormat fromResponse = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm");
        String reformatDate = null;
        try {
            reformatDate = myFormat.format(fromResponse.parse(lastUpdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tv_time.setText(reformatDate);
        holder.tv_condition.setText(listHour.get(position).getCondition().getText());
        holder.tv_temp.setText((int)listHour.get(position).getTemp_c() + "\u2103");
    }

    @Override
    public int getItemCount() {
        return listHour.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        public ImageView tv_icon;
        public TextView tv_time, tv_temp, tv_condition;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_icon = (ImageView)itemView.findViewById(R.id.hour_icon);
            tv_time = itemView.findViewById(R.id.hour_time);
            tv_temp = itemView.findViewById(R.id.hour_temp);
            tv_condition = itemView.findViewById(R.id.hour_condition);
        }
    }
}
