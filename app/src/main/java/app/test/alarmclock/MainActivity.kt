package app.test.alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    var alarmTimePicker: TimePicker? = null
    var peddingIntent: PendingIntent? = null
    var alarmManager: AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmTimePicker = findViewById(R.id.timePicker)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

    }

    fun onToggleClicked(view: android.view.View) {

        var time : Long

        if ((view as ToggleButton).isChecked){
            Toast.makeText(this, "ALARM ON", Toast.LENGTH_SHORT).show()

            var calendar = Calendar.getInstance()

            calendar[Calendar.HOUR_OF_DAY] = alarmTimePicker!!.currentHour
            calendar[Calendar.MINUTE] = alarmTimePicker!!.currentMinute

            var intent = android.content.Intent(this, AlarmReceiver::class.java)

            peddingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

            time = calendar.timeInMillis - calendar.timeInMillis % 60000

            if (System.currentTimeMillis() > time) {
                time = if (Calendar.AM_PM == 0)
                    time + 1000 * 60 * 60 * 12
                else {
                    time + 1000 * 60 * 60 * 24
                }
            }

            alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP, time, 1000, peddingIntent)
        }
        else{
            alarmManager!!.cancel(peddingIntent)
            Toast.makeText(this, "ALARM OFF", Toast.LENGTH_SHORT).show()
        }
    }
}