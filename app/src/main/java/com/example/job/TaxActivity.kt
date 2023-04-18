package com.example.job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class TaxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tax)

        val salary = findViewById(R.id.ed1) as EditText

        val tax = findViewById(R.id.ed2) as EditText

        val netsal = findViewById(R.id.ed3) as EditText

        val b1 = findViewById(R.id.btn1) as Button
        val b2 = findViewById(R.id.btn2) as Button

        b1.setOnClickListener {
            val va11 =salary.text.toString().toInt()
            val va12 :Double

            if (va11 <= 100000) {
                va12 = 0.0;
            } else if (va11 > 100000 && va11 <= 141667) {
                va12 = 0.06 * va11;
            } else if (va11 > 141667 && va11 <= 183333) {
                va12 = 0.12*va11;
            } else if (va11 > 183333 && va11 <= 225000) {
                va12 = 0.18*va11;
            } else if (va11 > 225000 && va11 <= 266667) {
                va12 = 0.24*va11;
            } else if (va11 > 266667 && va11 <= 308333) {
                va12 = 0.3*va11;
            } else {
                va12 = 0.36*va11 ;
            }

            tax.setText(va12.toString())

            val va13=va11-va12

            netsal.setText(va13.toString())

        }
    }
}