package info.jonatan_vahlberg.postit.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostItDatabaseDao {

    @Query("SELECT * from post_it_table ORDER by id DESC")
    fun getAllNotes(): LiveData<List<PostItNote>>

    @Query("SELECT * FROM post_it_table WHERE id = :key")
    fun get(key: Long): PostItNote?

    @Insert
    fun insert(note: PostItNote)

    @Update
    fun update(note: PostItNote)

    @Delete
    fun delete(note: PostItNote)

    @Query("DELETE FROM post_it_table")
    fun clear()
}