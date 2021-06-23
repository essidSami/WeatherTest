package com.sami.weathertest.ui.listing

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sami.weathertest.R
import com.sami.weathertest.data.model.CityWeatherResponse
import com.sami.weathertest.data.model.ResourceStatus
import com.sami.weathertest.databinding.FragmentAllWeatherBinding
import com.sami.weathertest.util.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [AllWeatherFragment] factory method to
 * create an instance of this fragment.
 */
class AllWeatherFragment : Fragment(R.layout.fragment_all_weather) {
    private var _binding: FragmentAllWeatherBinding? = null
    private val binding: FragmentAllWeatherBinding get() = requireNotNull(_binding)

    private val allWeatherViewModel: AllWeatherViewModel by sharedViewModel()
    private lateinit var weatherAdapter: WeatherAdapter
    private var mCountDownTimer: CountDownTimer? = null
    private var progressStatus = 0
    private var waitingMsgStatus = 1
    private var cityWeatherResponseList = mutableListOf<CityWeatherResponse>()
    private var millisInFuture = KEY_START_TIME_PROGRESS

    var handler: Handler = Handler()
    private lateinit var runnable: Runnable
    var delay = KEY_START_TIME_WAITING_MSG

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAllWeatherBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        //showing back button
        configureHomeToolBar(activity as AppCompatActivity, true)

        allWeatherViewModel.waitingMsg.value = 1

        //showing infinite waiting msg
        allWeatherViewModel.waitingMsg.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                1 -> {
                    binding.txtWaitingMsg.text = getString(R.string.txt_msg_waiting_first)
                }
                2 -> {
                    binding.txtWaitingMsg.text = getString(R.string.txt_msg_waiting_second)
                }
                3 -> {
                    binding.txtWaitingMsg.text = getString(R.string.txt_msg_waiting_third)
                }
            }
        })

        //get data weather by city
        allWeatherViewModel.progressStatus.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { status ->
                when (status) {

                    0 -> {
                        getCityWeather(getString(R.string.txt_city_rennes))
                    }
                    10 -> {
                        getCityWeather(getString(R.string.txt_city_paris))
                    }
                    20 -> {
                        getCityWeather(getString(R.string.txt_city_nantes))
                    }
                    30 -> {
                        getCityWeather(getString(R.string.txt_city_bordeaux))
                    }
                    40 -> {
                        getCityWeather(getString(R.string.txt_city_lyon))
                    }
                }

            })

        startWaitingMsg()

        binding.btnRestart.setOnClickListener{
            if (getConnectivityStatus(activity as AppCompatActivity)) {

                progressStatus = 0
                waitingMsgStatus = 1
                millisInFuture = KEY_START_TIME_PROGRESS

                binding.progressStatus.visibility = View.VISIBLE
                binding.txtProgressStatus.visibility = View.VISIBLE
                binding.txtWaitingMsg.visibility = View.VISIBLE
                binding.btnRestart.visibility = View.GONE

                startProgressBar()
                startWaitingMsg()

                binding.recyclerviewTableWeather.visibility = View.GONE

            }else{
                AlertUtility.showNeutralAlert(
                    activity,
                    getString(R.string.txt_alert_title),
                    getString(R.string.txt_alert_msg_internet),
                    getString(R.string.txt_btn_ok),
                    object :
                        NoticeDialogListener {
                        override fun onButtonClick(dialog: android.app.AlertDialog) {
                            dialog.dismiss()
                        }
                    },
                    false
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getCityWeather(city: String) {
        allWeatherViewModel.getCityWeather(city)
        allWeatherViewModel.getCityWeatherLiveData().observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                ResourceStatus.LOADING -> {
                }
                ResourceStatus.SUCCESS -> {
                    val weatherData = it.data as CityWeatherResponse
                    cityWeatherResponseList.add(weatherData)

                    allWeatherViewModel.clearResourceLiveData(allWeatherViewModel.getCityWeatherLiveData())
                }
                ResourceStatus.ERROR -> {
                    mCountDownTimer?.cancel()
                    mCountDownTimer = null

                    var message = resources.getString(R.string.txt_alert_msg_ws)
                    if (!getConnectivityStatus(activity as AppCompatActivity)) {
                        message = resources.getString(R.string.txt_alert_msg_internet)
                    }

                    AlertUtility.showNeutralAlert(
                        activity,
                        getString(R.string.txt_alert_title),
                        message,
                        getString(R.string.txt_btn_ok),
                        object :
                            NoticeDialogListener {
                            override fun onButtonClick(dialog: android.app.AlertDialog) {
                                dialog.dismiss()
                                activity?.onBackPressed()
                            }
                        },
                        false
                    )
                    allWeatherViewModel.clearResourceLiveData(allWeatherViewModel.getCityWeatherLiveData())
                }
            }
        })
    }

    /**
     * start Timer of progressbar
     */
    private fun startProgressBar(){
        mCountDownTimer = object : CountDownTimer(millisInFuture, KEY_COUNT_DOWN) {
            override fun onTick(millisUntilFinished: Long) {
                millisInFuture = millisUntilFinished
                binding.progressStatus.progress = progressStatus * 100 / (KEY_START_TIME_PROGRESS.toInt() / KEY_COUNT_DOWN.toInt())

                allWeatherViewModel.progressStatus.value = progressStatus

                binding.txtProgressStatus.text = "" + progressStatus * 100 / (KEY_START_TIME_PROGRESS.toInt() / KEY_COUNT_DOWN.toInt()) + " %"

                progressStatus++
            }

            override fun onFinish() {
                binding.progressStatus.progress = 100
                allWeatherViewModel.weatherResponseList.value = cityWeatherResponseList
                binding.txtWaitingMsg.visibility = View.INVISIBLE
                handler.removeCallbacks(runnable) //stop handler when activity not visible

                binding.progressStatus.visibility = View.GONE
                binding.txtProgressStatus.visibility = View.GONE
                binding.btnRestart.visibility = View.VISIBLE
                binding.recyclerviewTableWeather.visibility = View.VISIBLE
                setWeatherListData()
            }
        }
        mCountDownTimer?.start()
    }

    private fun startWaitingMsg(){
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable, delay.toLong())
            waitingMsgStatus++
            if (waitingMsgStatus == 4)
                waitingMsgStatus = 1
            allWeatherViewModel.waitingMsg.value = waitingMsgStatus
        }.also { runnable = it }, delay.toLong())
    }

    private fun setWeatherListData() {
        weatherAdapter = WeatherAdapter(cityWeatherResponseList)
        binding.recyclerviewTableWeather.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerviewTableWeather.setHasFixedSize(true)
        binding.recyclerviewTableWeather.isNestedScrollingEnabled = false
        binding.recyclerviewTableWeather.adapter = weatherAdapter
    }

    override fun onResume() {
        super.onResume()
        startProgressBar()
    }

    override fun onPause() {
        super.onPause()
        mCountDownTimer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimer?.cancel()
        mCountDownTimer = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}