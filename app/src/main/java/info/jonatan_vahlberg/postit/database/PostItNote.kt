package info.jonatan_vahlberg.postit.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "post_it_table")
data class PostItNote(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "post_message")
    var postMessage: String = "",

    @ColumnInfo(name = "post_time")
    var postTime: Long = System.currentTimeMillis()
)