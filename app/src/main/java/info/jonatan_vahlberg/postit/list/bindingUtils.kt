package info.jonatan_vahlberg.postit.list

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter
import info.jonatan_vahlberg.postit.database.PostItNote
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("formattedMessage")
fun TextView.setPostItNoteMessage(item: PostItNote) {
    text = item.postMessage
    textSize = 16f
    setTextColor( Color.BLACK)
}

@BindingAdapter("formattedTime")
fun TextView.setPostItTime(item: PostItNote) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm")
    val date = Date(item.postTime)
    text = dateFormat.format(date)
    setTypeface(null,Typeface.ITALIC)
    setTextColor( Color.DKGRAY)

}