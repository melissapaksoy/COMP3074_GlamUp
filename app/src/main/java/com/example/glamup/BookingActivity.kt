package com.example.glamup

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class BookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // Spinner (Services)
        val spinner: Spinner = findViewById(R.id.spinnerService)
        val services = listOf(
            "💅 Gel Manicure - $35",
            "✨ Nail Art Add-on - $15",
            "💖 Full Set Acrylic - $50"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, services)
        spinner.adapter = adapter

        // Date Picker
        val btnPickDate: Button = findViewById(R.id.btnPickDate)
        val txtSelectedDate: TextView = findViewById(R.id.txtSelectedDate)

        btnPickDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val dateString = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                    txtSelectedDate.text = "Selected Date: $dateString"
                }, year, month, day)

            datePicker.show()
        }

        // Confirm Booking Button
        val btnConfirm: Button = findViewById(R.id.btnConfirmBooking)
        btnConfirm.setOnClickListener {
            val chosenService = spinner.selectedItem.toString()
            val chosenDate = txtSelectedDate.text.toString()
            Toast.makeText(
                this,
                "Booking confirmed!\nService: $chosenService\n$chosenDate",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
