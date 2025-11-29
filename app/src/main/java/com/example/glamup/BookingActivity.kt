package com.example.glamup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*

class BookingActivity : AppCompatActivity() {

    private var selectedDate: String? = null
    private var selectedTime: String? = null

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

            val datePicker = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                    txtSelectedDate.text = "Selected Date: $selectedDate"
                }, year, month, day
            )

            datePicker.show()
        }

        // Time slot TextViews
        val tvTime1: TextView = findViewById(R.id.tvTime1)
        val tvTime2: TextView = findViewById(R.id.tvTime2)
        val tvTime3: TextView = findViewById(R.id.tvTime3)
        val timeViews = listOf(tvTime1, tvTime2, tvTime3)

        timeViews.forEach { timeView ->
            timeView.setOnClickListener {
                // Reset colors
                timeViews.forEach {
                    it.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                }
                // Highlight selected
                timeView.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_purple
                    )
                )
                selectedTime = timeView.text.toString()
            }
        }

        // Confirm Booking Button
        val btnConfirm: Button = findViewById(R.id.btnConfirmBooking)
        btnConfirm.setOnClickListener {
            val chosenService = spinner.selectedItem.toString()

            btnConfirm.isEnabled = false // prevents double-tap

            if (selectedDate == null || selectedTime == null) {
                Toast.makeText(this, "Please select a date and time", Toast.LENGTH_SHORT).show()
                btnConfirm.isEnabled = true
            } else {
                btnConfirm.postDelayed({
                    val intent = Intent(this, BookingConfirmationActivity::class.java)
                    intent.putExtra("SERVICE", chosenService)
                    intent.putExtra("DATE", selectedDate)
                    intent.putExtra("TIME", selectedTime)
                    startActivity(intent)
                }, 150)
            }
        }

    }
}



