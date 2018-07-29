package com.kaz.news_picker

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kaz.news_picker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        Log.d("onCreate","onCreate")

        binding.setVariable(BR.vm, viewModel)

//        viewModel.output
//                .
//                .subscribe(binding.textView.text.)

//        viewModel.topics
//                .observeOn(AndroidSchedulers.mainThread())


        viewModel.onCreate()
    }
}