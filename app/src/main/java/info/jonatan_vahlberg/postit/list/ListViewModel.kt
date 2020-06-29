package info.jonatan_vahlberg.postit.list

import android.app.Application
import android.widget.EditText
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import info.jonatan_vahlberg.postit.R
import info.jonatan_vahlberg.postit.database.PostItDatabase
import info.jonatan_vahlberg.postit.database.PostItDatabaseDao
import info.jonatan_vahlberg.postit.database.PostItNote
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.coroutines.*

class ListViewModel(val database: PostItDatabaseDao, application: Application): AndroidViewModel(application) {
    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val notes = database.getAllNotes()

    val currentMessage = MutableLiveData<String>()

    fun onSubmit(){
        uiScope.launch {
            var note = PostItNote()
            note.postMessage = currentMessage.value.toString()
            currentMessage.value = ""
            insertNote(note)
        }
    }



    private suspend fun insertNote(note: PostItNote) {
        return withContext(Dispatchers.IO){
            database.insert(note)

        }
    }


}