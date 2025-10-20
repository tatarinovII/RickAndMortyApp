package com.my.rickandmortyapp.activity.main

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.my.domain.models.Character
import com.my.rickandmortyapp.R
import com.my.rickandmortyapp.databinding.ActivityMainBinding
import com.my.rickandmortyapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm by viewModel<MainViewModel>()
    private lateinit var adapter: CharacterAdapter

    private lateinit var currentFilters: MutableMap<String, String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentFilters = mutableMapOf()

        setUpRecyclerView()
        setUpObservers()
        setUpClickListeners()

    }

    private fun setUpRecyclerView() {
        adapter = CharacterAdapter(onItemClick = { character ->
            showCharacterDescription(character)
        }, onLoadMore = {
            vm.loadPage(currentFilters)
        })
        binding.rv.layoutManager = GridLayoutManager(this, 2)
        binding.rv.adapter = adapter
    }

    private fun setUpObservers() {
        vm.characterList.observe(this) { list ->
            binding.progressBar.isVisible = false
            binding.swipeRefreshLayout.isRefreshing = false

            val isListEmpty = list.isEmpty()

            binding.rv.isVisible = !isListEmpty

            if (isListEmpty && (currentFilters.isNotEmpty() || adapter.itemCount > 0)) {
                binding.tvEmptySearch.isVisible = true
            } else {
                binding.tvEmptySearch.isVisible = false
            }

            adapter.updateItems(list)
        }

        vm.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                if (adapter.itemCount == 0) {
                    binding.progressBar.isVisible = true
                }
                binding.tvEmptySearch.isVisible = false
            } else {
                binding.progressBar.isVisible = false
                if (binding.swipeRefreshLayout.isRefreshing) {
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
            adapter.setLoading(isLoading)
        }
    }

    private fun setUpClickListeners() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding.etSearch.text.isNotEmpty()) {
                currentFilters["name"] = binding.etSearch.text.toString()
                vm.loadPage(currentFilters, true)
            }
            false
        }

        binding.bClear.setOnClickListener {
            currentFilters.remove("name")
            binding.etSearch.text.clear()
            vm.loadPage(currentFilters, true)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            currentFilters.remove("name")
            binding.etSearch.text.clear()
            vm.loadPage(currentFilters, true)
        }


        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
                binding.bClear.isVisible = !s.isNullOrEmpty()
            }

        }
        binding.etSearch.addTextChangedListener(textWatcher)

        binding.bFilters.setOnClickListener {
            showFiltersDialog()
        }
    }

    private fun showCharacterDescription(character: Character) {
        val dialogBinding = layoutInflater.inflate(R.layout.character_description, null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        myDialog.setCancelable(true)
        dialogBinding.findViewById<TextView>(R.id.etId).text = character.id.toString()
        dialogBinding.findViewById<TextView>(R.id.etName).text = character.name
        dialogBinding.findViewById<TextView>(R.id.etStatus).text = character.status
        dialogBinding.findViewById<TextView>(R.id.etSpecies).text = character.species
        dialogBinding.findViewById<TextView>(R.id.etType).text = character.type
        dialogBinding.findViewById<TextView>(R.id.etGender).text = character.gender
        dialogBinding.findViewById<TextView>(R.id.etOrigin).text = character.origin
        dialogBinding.findViewById<TextView>(R.id.etLocation).text = character.location
        myDialog.show()
        val btnClose = dialogBinding.findViewById<Button>(R.id.btCloseDescription)
        btnClose.setOnClickListener {
            myDialog.dismiss()
        }
    }

    private fun showFiltersDialog() {
        val dialog = FiltersDialogFragment()

        dialog.onApplyFilters = { status, gender ->
            currentFilters.clear()
            if (status != null) currentFilters["status"] = status
            if (gender != null) currentFilters["gender"] = gender
            vm.loadPage(currentFilters, true)
        }
        dialog.show(supportFragmentManager, "FiltersDialogFragment")
    }
}