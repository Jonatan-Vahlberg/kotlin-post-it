package info.jonatan_vahlberg.postit.list

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import info.jonatan_vahlberg.postit.R
import info.jonatan_vahlberg.postit.Utils
import info.jonatan_vahlberg.postit.database.PostItDatabase
import info.jonatan_vahlberg.postit.databinding.FragmentListBinding
import info.jonatan_vahlberg.postit.hideKeyboard

class ListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PostItDatabase.getDatabase(application).postItDatabaseDao
        val viewModelFactory = ListViewModelFactory(dataSource,application)
        val viewModel = ViewModelProviders.of(this,viewModelFactory).get(ListViewModel::class.java)


        val adapter = PostItAdapter(dataSource)
        binding.notesList.adapter = adapter
        viewModel.notes.observe(viewLifecycleOwner, Observer {
            println("ROOM UPDATE NEW NOTE "+ it.size)
            it?.let {
                adapter.submitList(it)
            }
            binding.postItInput.clearFocus()
            this.context?.let { it1 -> Utils.hideSoftKeyBoard(it1,binding.root) }
            if(binding.postItInput.hasFocus()){

            }
        })

        binding.setLifecycleOwner(this)
        binding.listViewModel = viewModel

        return binding.root
    }
}