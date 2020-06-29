package info.jonatan_vahlberg.postit

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import info.jonatan_vahlberg.postit.database.PostItDatabase
import info.jonatan_vahlberg.postit.database.PostItDatabaseDao
import info.jonatan_vahlberg.postit.database.PostItNote
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class PostItRoomTest {
    private lateinit var postItDao: PostItDatabaseDao
    private  lateinit var db: PostItDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context,PostItDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        postItDao = db.postItDatabaseDao
    }

    @After
    @Throws(Exception::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetNote() {
        val note = PostItNote()
        note.postMessage = "First actual note"
        postItDao.insert(note)

        val roomNote = postItDao.get(1)
        println("ROOM TEST" + roomNote?.postMessage)
        assert(roomNote != null)
    }

    @Test
    @Throws(IOException::class)
    fun insertAndUpdateNote() {
        val note = PostItNote()
        val secondNote = PostItNote()
        note.postMessage = "First actual note"
        secondNote.postMessage = "Second actual note"
        postItDao.insert(note)
        postItDao.insert(secondNote)

        val roomNote = postItDao.get(2)
        println("ROOM TEST" +roomNote?.postMessage)

        if (roomNote != null) {
            roomNote.postMessage ="Updated message"
            postItDao.update(roomNote)
        }
        assert(postItDao.get(2) != null && postItDao.get(2)!!.postMessage.contains("Updated"))
    }
}