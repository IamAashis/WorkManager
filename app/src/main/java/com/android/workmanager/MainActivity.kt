package com.android.workmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
        clickListener()
    }

    private val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "MM/dd/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.UK)
            binding?.txvDate?.text = sdf.format(cal.time)
//            this.year= cal.get(Calendar.YEAR)
//            this.day = cal.get(Calendar.MONTH)
//            this.month = cal.get(Calendar.MONTH)
        }
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

/*            NotificationMan.Builder(context = this, classPathWillBeOpen = "com.notification.man.MainActivity") // the activity's path that you want to open when the notification is clicked
                .setTitle(title = "test title") // optional
                .setDescription(desc = "test desc") // optional
                .setThumbnailUrl(thumbnailUrl = "image url") // optional
                .setTimeInterval(timeInterval = 10L) // needs secs - default is 5 secs
                .setNotificationType(type = NotificationTypes.IMAGE.type) // optional - default type is TEXT
                .setNotificationChannelConfig(config = createNotificationManChannelConfig()) // optional
                .fire()*/
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


}