package com.gsm.phoneuse.screenTime


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gsm.phoneuse.databinding.CountingLayoutBinding
import com.gsm.phoneuse.persistence.ScreenTimeDatabase
import com.gsm.phoneuse.services.ScreenTimeService
import kotlinx.coroutines.*
import kotlin.system.exitProcess

class ScreenTimeFragment : Fragment() {

    private lateinit var screenTimeViewModel: ScreenTimeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: CountingLayoutBinding = DataBindingUtil.inflate(inflater, com.gsm.phoneuse.R.layout.counting_layout, container, false)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application

        val dataSource = ScreenTimeDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = ScreenTimeViewModelFactory(dataSource, application)

        screenTimeViewModel = ViewModelProviders.of(
            this, viewModelFactory).get(ScreenTimeViewModel::class.java)

        binding.screenTimeViewModel = screenTimeViewModel

        screenTimeViewModel.timeString.observe(this, Observer { binding.timeScreenOn.text = it })

        binding.closeButton.setOnClickListener {
            exitProcess(-1)
        }

        context?.startService(Intent(context, ScreenTimeService::class.java))

        return binding.root
    }

}

