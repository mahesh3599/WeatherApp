package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.data.SharedPreferencesCities;
import com.example.weatherapp.model.WeatherModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/*
* Recycler view for storing searched cities.
* */
public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<WeatherModel> weathers = new ArrayList<>();
    private OnWeatherItemListener onWeatherItemListener;
    private SharedPreferencesCities sharedPreferencesCities;

    public WeatherRecyclerViewAdapter(OnWeatherItemListener onWeatherItemListener) {
        this.onWeatherItemListener = onWeatherItemListener;
    }

    public void setWeathers(List<WeatherModel> weathers, SharedPreferencesCities sharedPreferencesCities) {
        this.weathers = weathers;
        this.sharedPreferencesCities = sharedPreferencesCities;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.weathers.remove(position);
        this.sharedPreferencesCities.saveWeather(weathers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        return new WeatherViewHolder(view, onWeatherItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((WeatherViewHolder)holder).location.setText(weathers.get(position).getName());

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String temperature = decimalFormat.format(weathers.get(position).getMain().getTemp() - 273.15);

        ((WeatherViewHolder)holder).temperature.setText(temperature + "°C");

        Glide.with(holder.itemView.getContext())
                .load("https://openweathermap.org/img/w/" + weathers.get(position).getWeather().get(0).getIcon() + ".png")
                .into(((WeatherViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    public WeatherModel getSelectedWeather(int position){

        if (weathers != null){
            if (weathers.size() > 0){
                return weathers.get(position);
            }
        }

        return null;
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView location, temperature;
        ImageView imageView;
        Button removeButton;

        OnWeatherItemListener onWeatherItemListener;

        public WeatherViewHolder(@NonNull View itemView, OnWeatherItemListener onWeatherItemListener) {
            super(itemView);

            this.onWeatherItemListener = onWeatherItemListener;

            location = itemView.findViewById(R.id.location);
            temperature = itemView.findViewById(R.id.temperature);
            imageView = itemView.findViewById(R.id.pic);
            removeButton = itemView.findViewById(R.id.remove);
            removeButton.setOnClickListener(v-> {
                removeItem(getAdapterPosition());
            });

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onWeatherItemListener.onWeatherItemClickListener(getAdapterPosition());
        }
    }

}
