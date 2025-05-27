package com.example.factorialtest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.factorialtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeViewModel()

        binding.buttonCalculate.setOnClickListener{
            val text = binding.editTextNumber.text.toString()
            viewModel.calculate(text)
        }
    }

    private fun observeViewModel() {
        viewModel.progress.observe(this) {
            if (it) {
                binding.progressBarLoading.visibility = View.VISIBLE
                binding.buttonCalculate.isEnabled = false
            } else {
                binding.progressBarLoading.visibility = View.GONE
                binding.buttonCalculate.isEnabled = true
            }
        }

        viewModel.error.observe(this) {
            if (it == true) {
                Toast.makeText(this, "You did not entered the value", Toast.LENGTH_SHORT).show()
                viewModel.resetError()
            }
        }

        viewModel.factorial.observe(this) {
            binding.textViewFactorial.text = it
        }
    }
}