package com.example.sqldelightexamplee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqldelightexamplee.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import example.persondb.PersonEntity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PersonListViewModel

    private var adapter: DataAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)

        binding.lifecycleOwner = this
        // viewmodel
        binding.loginViewModel = viewModel

        adapter = DataAdapter()

        val linearLayoutManager = LinearLayoutManager(this)
        binding.rvData.layoutManager = linearLayoutManager

        //viewModel.persons
        binding.rvData.adapter = adapter

        lifecycleScope.launch {
            viewModel.persons.flowWithLifecycle(lifecycle)
                .collect {
                    it.let {
                        adapter?.setUsers(it)
                    }
                }
        }

        binding.okButton.setOnClickListener {
            viewModel.onInsertPerson(binding.firstnameEdittext.text.toString(), binding.lastnameEdittext.text.toString())
            binding.firstnameEdittext.setText("")
            binding.lastnameEdittext.setText("")
        }

        // Interface Click Listener..
        onClick()
    }

    /** Interface click listener */
    private fun onClick() {
        adapter?.setOnButtonClickListener(object :
            DataAdapter.OnButtonClickListener {
            override fun onButtonCLick(
                position: Int,
                model: PersonEntity?,
                action: String?
            ) {
                if (action.equals("delete", ignoreCase = true)) {
                    viewModel.onDeleteClick(model?.id!!)
                }

                if(action.equals("details", ignoreCase = true)) {
                    // Open dialog and display data
                    ErrorDialog.showErrorDialog(this@MainActivity, model?.firstname, model?.lastname)
                }
            }
        })
    }

}