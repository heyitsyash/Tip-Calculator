package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCheck.setOnClickListener{calculateTip()}

        binding.costOfServiceEditText.setOnKeyListener{view, keyCode , _ -> handleKeyEvent(view,keyCode)}
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

    private fun calculateTip() {

       // Calling toDouble() on a string that is empty or a string that doesn't represent a valid decimal number doesn't work.
        // Fortunately Kotlin also provides a method called toDoubleOrNull() which handles these problems.
       // It returns a decimal number(0.0) if it can, or it returns null if there's a problem

        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()

        if (cost == null){
            binding.tipAmount.text = "" //so it does not show previous amount
            return
        }

        val resourceId = binding.tipOptions.checkedRadioButtonId
        val percentage = when(resourceId){

            R.id._option_amazing -> 0.20
            R.id._option_good -> 0.18
            else -> 0.08
        }

        var tip = cost*percentage

        val roundUp = binding.switchButton.isChecked

        if(roundUp){
            tip = kotlin.math.ceil(tip)
        }

        val currencyFormated = NumberFormat.getCurrencyInstance().format(tip)

        binding.tipAmount.text = "Tip Amount: $currencyFormated"
    }
}