package edu.uw.ischool.msk812.tipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    lateinit var amountEdit: EditText
    lateinit var tipButton: Button
    lateinit var tipPercent: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amountEdit = findViewById(R.id.amountText)
        tipButton = findViewById(R.id.tipButton)
        tipPercent = findViewById(R.id.tipSpinner)

        tipButton.isEnabled = false
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.percent_array,
            android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipPercent.adapter = adapter


        amountEdit.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                if (input.isNotEmpty() && input.matches(Regex("^(\\d*\\.)?\\d+\$"))) {
                    tipButton.isEnabled = true
                } else {
                    tipButton.isEnabled = false
                }
            }
        })

        tipButton.setOnClickListener {
            val amount = amountEdit.text.toString().toDouble()
            val convertPercent = tipPercent.selectedItem.toString().replace("%", "").toDouble()
            val tipAmount = amount * (convertPercent / 100)
            val formatTip = NumberFormat.getCurrencyInstance().format(tipAmount)
            val toastMessage = "Tip amount $formatTip"
            val toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT)
            toast.show()
        }

    }
}