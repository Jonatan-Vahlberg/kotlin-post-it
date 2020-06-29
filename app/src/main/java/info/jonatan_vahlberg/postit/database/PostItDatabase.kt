package info.jonatan_vahlberg.postit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PostItNote::class],version = 1,exportSchema = false)
abstract class PostItDatabase: RoomDatabase() {
    abstract val postItDatabaseDao: PostItDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PostItDatabase? = null

        fun getDatabase(context: Context): PostItDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostItDatabase::class.java,
                    "post_it_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }
    }
}