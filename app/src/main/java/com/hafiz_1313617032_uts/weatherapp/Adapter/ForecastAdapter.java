package com.hafiz_1313617032_uts.weatherapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.ForecastDayModel;
import com.hafiz_1313617032_uts.weatherapp.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ListViewHolder> {

    ArrayList<ForecastDayModel> listForecastDay = new ArrayList<>();
    private OnForecastListener mOnForecastListener;

    public ForecastAdapter(ArrayList<ForecastDayModel> mlistForecastDay, OnForecastListener onForecastListener) {
        this.listForecastDay = mlistForecastDay;
        this.mOnForecastListener = onForecastListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_forecast, parent, false);
        ListViewHolder listViewHolder = new ListViewHolder(view, mOnForecastListener);
        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        String lastUpdate = listForecastDay.get(position).getDate();
        SimpleDateFormat fromResponse = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("EEEE, dd MMM");
        String reformatDate = null;
        try {
            reformatDate = myFormat.format(fromResponse.parse(lastUpdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tv_date.setText(reformatDate);
        holder.tv_avgTemp.setText((int)listForecastDay.get(position).getDay().getAvgtemp_c() + "\u2103");
        holder.tv_condition.setText(listForecastDay.get(position).getDay().getCondition().getText());
        Picasso.get().load("https:" + listForecastDay.get(position).getDay().getCondition().getIcon()).into(holder.tv_icon);
    }

    @Override
    public int getItemCount() {
        return listForecastDay.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tv_date, tv_avgTemp, tv_condition;
        public ImageView tv_icon;
        OnForecastListener onForecastListener;

        public ListViewHolder(@NonNull View itemView, OnForecastListener onForecastListener) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.forecast_date);
            tv_avgTemp = itemView.findViewById(R.id.forecast_avg_temp);
            tv_condition = itemView.findViewById(R.id.forecast_condition);
            tv_icon = (ImageView)itemView.findViewById(R.id.forecast_icon);
            this.onForecastListener = onForecastListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onForecastListener.onForecastClick(getAdapterPosition());
        }
    }

    public interface OnForecastListener {
        void onForecastClick(int position);
    }
}
