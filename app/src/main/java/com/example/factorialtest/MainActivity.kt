package com.example.factorialtest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        binding.buttonCalculate.setOnClickListener {
            val text = binding.editTextNumber.text.toString()
            viewModel.calculate(text)
        }
    }

    private fun observeViewModel() {
        binding.progressBarLoading.visibility = View.GONE
        binding.buttonCalculate.isEnabled = true

        viewModel.state.observe(this) {
            when (it) {
                is Error -> {
                    Toast.makeText(
                        this,
                        "You did not entered the value",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Progress -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.buttonCalculate.isEnabled = false
                }

                is Result -> {
                    binding.textViewFactorial.text = it.factorial
                }
            }

        }
    }

}