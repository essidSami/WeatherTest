package com.sami.weathertest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sami.weathertest.R
import com.sami.weathertest.databinding.FragmentHomeBinding
import com.sami.weathertest.ui.listing.AllWeatherFragment
import com.sami.weathertest.util.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        //showing back button
        configureHomeToolBar(activity as AppCompatActivity, false)

        binding.btnGo.setOnClickListener{
            if (getConnectivityStatus(activity as AppCompatActivity)) {
                replaceFragment((activity as AppCompatActivity).supportFragmentManager, AllWeatherFragment(), true)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}