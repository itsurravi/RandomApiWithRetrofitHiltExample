package com.ravikant.randomapiwithretrofithiltexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ravikant.randomapiwithretrofithiltexample.R
import com.ravikant.randomapiwithretrofithiltexample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSingle.setOnClickListener {
            viewModel.getAppData()
        }

        binding.btnList.setOnClickListener {
            viewModel.getAppListData(2)
        }

        lifecycleScope.launch {
            viewModel.appData.collect {
                when(it) {
                    is MainViewModel.AppInfo.SuccessSingleData -> {
                        binding.progressBar.isVisible = false
                        Log.e("dataCollect", "${it.data}")
                    }
                    is MainViewModel.AppInfo.SuccessListData -> {
                        binding.progressBar.isVisible = false
                        Log.e("dataCollect", "${it.data}")
                    }
                    is MainViewModel.AppInfo.Failure -> {
                        binding.progressBar.isVisible = false
                        Log.e("dataCollect", "data: ${it.error}")
                    }
                    is MainViewModel.AppInfo.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is MainViewModel.AppInfo.Empty -> {
                        binding.progressBar.isVisible = false
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}