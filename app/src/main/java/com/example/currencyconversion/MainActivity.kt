package com.example.currencyconversion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var moneyFrom: EditText
    private lateinit var moneyTo: EditText
    private lateinit var convertFrom: Spinner
    private lateinit var convertTo: Spinner

    // Tạo một bảng tỷ giá chuyển đổi từ VND
    private val conversionRates = mapOf(
        "VND" to 1.0,
        "AED" to 0.00014493,
        "AFN" to 0.002638,
        "ALL" to 0.003582,
        "AMD" to 0.01515,
        "ANG" to 0.00007064,
        "AOA" to 0.03593,
        "ARS" to 0.03904,
        "AUD" to 0.00006015,
        "BZD" to 0.00007893,
        "CAD" to 0.00005490,
        "CHF" to 0.00003424,
        "EUR" to 0.00003649,
        "GBP" to 0.00003035,
        "USD" to 0.00003947,
        // Thêm các tỷ giá khác nếu cần...
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Khởi tạo views
        moneyFrom = findViewById(R.id.money_from)
        moneyTo = findViewById(R.id.money_to)
        convertFrom = findViewById(R.id.convert_from)
        convertTo = findViewById(R.id.convert_to)

        // Cài đặt danh sách dropdown
        val dropDownList = conversionRates.keys.toTypedArray()
        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropDownList)
        convertFrom.adapter = adapter
        convertTo.adapter = adapter

        // Thêm listener cho EditText
        moneyFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = convertCurrency()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Thêm listener cho Spinner
        convertFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        convertTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Thiết lập listener cho EditText để xác định nguồn và đích
        moneyFrom.setOnClickListener {
            convertFrom.requestFocus()
            convertTo.clearFocus()
        }

        moneyTo.setOnClickListener {
            convertTo.requestFocus()
            convertFrom.clearFocus()
        }
    }

    private fun convertCurrency() {
        // Lấy giá trị từ EditText
        val amount = moneyFrom.text.toString().toDoubleOrNull() ?: return
        val fromCurrency = convertFrom.selectedItem.toString()
        val toCurrency = convertTo.selectedItem.toString()

        // Tính toán tỷ giá
        val fromRate = conversionRates[fromCurrency] ?: return
        val toRate = conversionRates[toCurrency] ?: return

        // Thực hiện chuyển đổi
        val result = amount * (toRate / fromRate)
        moneyTo.setText(String.format("%.2f", result))
    }
}