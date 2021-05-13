package com.culturetrip.article.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.culturetrip.article.R
import com.culturetrip.article.databinding.ActivityMainBinding
import com.culturetrip.article.ui.articles.ArticlesActivity
import com.culturetrip.article.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Observer

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupObservers(binding)
        setListeners(binding)
    }

    private fun setListeners(binding: ActivityMainBinding) {
        binding.loadBtn.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "setListeners: ")
            viewModel.fetchArticles()
        })
    }

    private fun setupObservers(binding: ActivityMainBinding) {

        viewModel.loadedStatus.observe(this) {
            if (it) {
                startActivity(Intent(this, ArticlesActivity::class.java))
            }
        }

        // show the spinner when [MainViewModel.spinner] is true
        viewModel.spinner.observe(this) { value ->
            value.let { show ->
                binding.spinner.visibility = if (show) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

        // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
        viewModel.toastMsg.observe(this) { text ->
            text?.let {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
        }
    }


}