package com.my.rickandmortyapp.activity.main

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.my.rickandmortyapp.R
import com.my.rickandmortyapp.databinding.ActivityMainBinding
import com.my.rickandmortyapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm by viewModel<MainViewModel>()
    private lateinit var adapter: CharacterAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CharacterAdapter(mutableListOf(), onItemClick = {})
        binding.rv.layoutManager = GridLayoutManager(this, 2)
        binding.rv.adapter = adapter

        setUpObservers()
    }

    private fun setUpObservers() {
        vm.characterList.observe(this) { list ->
            adapter.addItems(list)
        }
    }
}