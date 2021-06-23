package com.sami.weathertest.ui.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sami.weathertest.BuildConfig
import com.sami.weathertest.R
import com.sami.weathertest.data.model.CityWeatherResponse
import com.sami.weathertest.util.KEY_KELVIN_TO_CELSIUS
import kotlinx.android.synthetic.main.row_item_weather.view.*

class WeatherAdapter (
    private val items: MutableList<CityWeatherResponse>
) :
    RecyclerView.Adapter<WeatherHolder>() {

    /**
     * Implementation Section
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_weather, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

/**
 * View holder for Stories [View]
 */
class WeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        weatherData: CityWeatherResponse
    ) {
        with(itemView) {

            txt_city_name.text = weatherData.name

            txt_max_temp.text = String.format(
                context.getString(
                    R.string.txt_temperature_value,
                    weatherData.temperature?.tempMax?.minus(KEY_KELVIN_TO_CELSIUS)
                )
            )

            txt_min_temp.text = String.format(
                context.getString(
                    R.string.txt_temperature_value,
                    weatherData.temperature?.tempMin?.minus(KEY_KELVIN_TO_CELSIUS)
                )
            )

            Glide.with(context)
                .load(BuildConfig.IMG_URL + weatherData.weather?.get(0)?.icon + "@2x.png")
                .circleCrop()
                .skipMemoryCache(true)
                .into(img_cloud)
        }
    }

}
