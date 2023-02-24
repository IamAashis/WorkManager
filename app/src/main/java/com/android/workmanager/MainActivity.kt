package com.android.workmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.android.workmanager.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    var cal = Calendar.getInstance()
    var year = 0;
    var month = 0;
    var day = 0;
    var hour = 0;
    var minute = 0;
    var timeMiliSecond = ""
    var workManager: WorkManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        clickListener()
    }

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "MM/dd/yyyy" // mention the format you needpo
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            binding?.txvDate?.text = sdf.format(cal.time)
            //            this.year= cal.get(Calendar.YEAR)
            //            this.day = cal.get(Calendar.MONTH)
            //            this.month = cal.get(Calendar.MONTH)
        }

    fun clickListener() {
        binding?.txtDate?.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding?.txttime?.setOnClickListener {
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR),
                cal.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this)
            ).show()
        }
        binding?.btnSubmit?.setOnClickListener {
            startWork()
        }
    }

    private val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
            val myFormat = "EEE, d MMM yyyy HH:mm:ss Z"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            this@MainActivity.minute = cal.get(Calendar.MINUTE)
            hour = cal.get(Calendar.HOUR)
            cal.set(Calendar.HOUR, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            binding?.txvTime?.text = sdf.format(cal.time)
        }
    }

    fun createConstraints() = Constraints.Builder()
        .setRequiresCharging(true)
        .setRequiresBatteryNotLow(true)
        .build()

    private fun createWorkRequest(data: Data) =
        PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(createConstraints())
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

    private fun startWork() {
        val work = createWorkRequest(Data.EMPTY)
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork("Sleep work", ExistingPeriodicWorkPolicy.REPLACE, work)
    }

}