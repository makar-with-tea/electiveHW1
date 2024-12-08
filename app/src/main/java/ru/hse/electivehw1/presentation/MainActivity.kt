package ru.hse.electivehw1.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import ru.hse.electivehw1.R

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val sigma2 = findViewById<EditText>(R.id.variance_value)
        val mu = findViewById<EditText>(R.id.mean_val)
        val randomNumber = findViewById<TextView>(R.id.random_number_result)
        val getNumberButton =  findViewById<Button>(R.id.get_random_num)

        viewModel.randomNumber.observe(this) { value ->
            randomNumber.text = value?.toString() ?: ""
        }
        // для mu и sigma2 сохранение происходит автоматически благодаря bundle

        getNumberButton.setOnClickListener {
            viewModel.generateNumber()
        }

        mu.doOnTextChanged { text, _, _, _ ->
            viewModel.setMu(text.toString().toDoubleOrNull())
        }

        sigma2.doOnTextChanged { text, _, _, _ ->
            viewModel.setSigma2(text.toString().toDoubleOrNull())
        }

        viewModel.toastMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.clearToastMessage()
            }
        }

    }
}