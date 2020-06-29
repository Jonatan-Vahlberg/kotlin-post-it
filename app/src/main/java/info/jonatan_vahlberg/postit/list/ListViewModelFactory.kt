package info.jonatan_vahlberg.postit.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import info.jonatan_vahlberg.postit.database.PostItDatabaseDao
import java.lang.IllegalArgumentException

class ListViewModelFactory(private val  dataSource: PostItDatabaseDao, private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListViewModel::class.java)){
            return ListViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unkown Viewmodel class")
    }
}