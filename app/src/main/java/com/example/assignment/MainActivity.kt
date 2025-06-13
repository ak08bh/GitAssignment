package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.adapter.GitRepoListAdapter
import com.example.assignment.adapter.RepoClickListener
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.model.Item
import com.example.assignment.model.ResponseModel
import com.example.assignment.preference.ThemePreferenceManager
import com.example.assignment.viewModel.GitRepoViewModel
import com.example.assignment.viewModel.GitRepoViewModelFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GitRepoListAdapter
    private lateinit var themePrefs: ThemePreferenceManager

    private var isDarkTheme: Boolean = false

    private val viewModel: GitRepoViewModel by viewModels {
        GitRepoViewModelFactory((application as GitApplication).networkRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        themePrefs = ThemePreferenceManager(this)
        isDarkTheme = themePrefs.loadThemePreference()

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupMenu()

        adapter = GitRepoListAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        observeData()

        adapter.setOnClickListener(object : RepoClickListener {
            override fun onClick(data: Item?, position: Int) {
                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                intent.putExtra("url", data?.html_url)
                startActivity(intent)
            }
        })
    }

    private fun setupMenu() {
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.options_menu, menu)

                // Setup search
                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem.actionView as SearchView
                searchView.queryHint = "Search with ID or name"

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.getSearchData(newText.orEmpty())
                        return true
                    }
                })

                // Theme toggle
                val switchItem = menu.findItem(R.id.switchButton)
                val toggleView = switchItem.actionView
                val toggleImage = toggleView?.findViewById<ImageView>(R.id.themeToggle)

                toggleImage?.setOnClickListener {
                    toggleTheme()
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, this, Lifecycle.State.RESUMED)
    }

    private fun toggleTheme() {
        isDarkTheme = !isDarkTheme
        themePrefs.saveThemePreference(isDarkTheme)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        // Activity will automatically recreate on night mode change
    }

    private fun observeData() {
        viewModel.list.observe(this) { state ->
            when (state) {
                is ResponseModel.Loading -> {
                    binding.progress.isVisible = true
                    binding.error.isVisible = false
                }
                is ResponseModel.Success -> {
                    binding.progress.isVisible = false
                    binding.error.isVisible = false
                    adapter.submitList(state.data)
                }
                is ResponseModel.Error -> {
                    binding.progress.isVisible = false
                    binding.error.text = state.message
                    binding.error.isVisible = true
                }
            }
        }

        // Load cached data from Room
        viewModel.listItemRoom.observe(this) { list ->
            if (!list.isNullOrEmpty()) {
                adapter.submitList(list)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
