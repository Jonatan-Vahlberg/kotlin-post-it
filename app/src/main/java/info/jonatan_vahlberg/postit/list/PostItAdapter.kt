package info.jonatan_vahlberg.postit.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import info.jonatan_vahlberg.postit.database.PostItDatabaseDao
import info.jonatan_vahlberg.postit.database.PostItNote
import info.jonatan_vahlberg.postit.databinding.PostItListItemBinding
import kotlinx.coroutines.*

class PostItAdapter(private  val database: PostItDatabaseDao): ListAdapter<PostItNote,PostItAdapter.ViewHolder>(PostItDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, database)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private  constructor(val binding: PostItListItemBinding, private val database: PostItDatabaseDao ): RecyclerView.ViewHolder(binding.root){
        private var viewModelJob = Job()
        private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        fun bind(item: PostItNote){
            binding.note = item
            binding.noteClose.setOnClickListener {
                uiScope.launch {
                        deleteNote(item)
                    }
            }
            binding.executePendingBindings()
        }

        private suspend fun deleteNote(item: PostItNote) {
            return withContext(Dispatchers.IO){
                database.delete(item)
            }
        }

        companion object{
            fun from(parent: ViewGroup, database: PostItDatabaseDao): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = PostItListItemBinding.inflate(layoutInflater,parent,false)

                return ViewHolder(binding,database)
            }
        }
    }

    class PostItDiffCallback: DiffUtil.ItemCallback<PostItNote>() {
        override fun areItemsTheSame(oldItem: PostItNote, newItem: PostItNote): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostItNote, newItem: PostItNote): Boolean {
            return oldItem == newItem
        }

    }


}